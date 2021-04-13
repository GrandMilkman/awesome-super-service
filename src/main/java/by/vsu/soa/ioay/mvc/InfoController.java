package by.vsu.soa.ioay.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import by.vsu.soa.ioay.mvc.view.InfoDocxView;

@Controller
public class InfoController {

    @Autowired
    private InfoDocxView infoDocxView;

    @Autowired
    private MessageSource messages;

    @Autowired
    private ApplicationContext ctx;

    @GetMapping("/info")
    public String info(final Model model) {

        List<String> list = new ArrayList<>();

        for (String name : ctx.getBeanDefinitionNames()) {
            list.add(name + " : " + ctx.getBean(name).getClass().getSimpleName());
        }

        model.addAttribute("list", list);

        return "info";
    }

    @GetMapping("/info.docx")
    public ModelAndView docx(final HttpServletRequest req, final Model model) {

        List<Info> list = new ArrayList<>();

        for (String name : ctx.getBeanDefinitionNames()) {
            list.add(new Info(name, ctx.getBean(name).getClass().getSimpleName()));
        }

        model.addAttribute("list", list);

        model.addAttribute("_filename_", messages.getMessage("filename.docx", null, req.getLocale()));

        return new ModelAndView(infoDocxView, model.asMap());
    }

    public static class Info {
        private String name;

        private String value;

        public Info(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
