package by.vsu.soa.ioay;

import javax.sql.DataSource;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@EnableTransactionManagement
public class Main /*extends SpringBootServletInitializer*/ {

    @Value("${server.ajp.enable:false}") private Boolean ajpEnable;

    @Value("${server.ajp.port:8009}") private Integer ajpPort;

    @Value("${server.ajp.secure:false}") private Boolean ajpSecure;

    @Value("${server.ajp.scheme:http}") private String ajpScheme;

    @Value("${server.ajp.address:localhost}") private String ajpAddress;

    @Bean("taskExecutor")
    public AsyncTaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        return executor;
    }

    @Bean("liquibase")
    public SpringLiquibase springLiquibase(
            @Autowired final DataSource dataSource,
            @Value("${liquibase.changelog}") final String changeLog,
            @Value("${liquibase.contexts}") final String contexts) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLog);
        liquibase.setContexts(contexts);
        return liquibase;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
            @Override
            public void customize(final TomcatServletWebServerFactory factory) {
                if (ajpEnable) {
                    final Connector connector = new Connector("AJP/1.3");
                    connector.setPort(ajpPort);
                    connector.setSecure(ajpSecure);
                    connector.setScheme(ajpScheme);
                    connector.setProperty("address", ajpAddress);
                    ((AbstractAjpProtocol<?>) connector.getProtocolHandler()).setSecretRequired(false);
                    factory.addAdditionalTomcatConnectors(connector);
                }
            }
        };
    }

    public static void main(final String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
        ctx.start();
    }
}
