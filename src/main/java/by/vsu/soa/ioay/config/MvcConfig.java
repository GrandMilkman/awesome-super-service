package by.vsu.soa.ioay.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en", "US"));
        return localeResolver;
    }

    @Bean
    public ThemeSource themeSource() {
        final ResourceBundleThemeSource source = new ResourceBundleThemeSource();
        source.setBasenamePrefix("/themes/theme-");
        return source;
    }

    @Bean
    public ThemeResolver themeResolver(
            @Value("${theme.cookie.maxage:2400}") final Integer cookieMaxAge,
            @Value("${theme.cookie.name:theme}") final String cookieName) {
        final CookieThemeResolver resolver = new CookieThemeResolver();
        resolver.setCookieMaxAge(cookieMaxAge);
        resolver.setCookieName(cookieName);
        resolver.setDefaultThemeName("default");
        return resolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);

        final ThemeChangeInterceptor themeInterceptor = new ThemeChangeInterceptor();
        themeInterceptor.setParamName("theme");
        registry.addInterceptor(themeInterceptor);
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index");
        registry.addRedirectViewController("/logout", "/");

        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/login").setViewName("login");
    }

    @Autowired
    public void addDialect(final SpringTemplateEngine engine) {
        engine.addDialect(new SpringSecurityDialect());
        engine.addDialect(new Java8TimeDialect());
    }
}
