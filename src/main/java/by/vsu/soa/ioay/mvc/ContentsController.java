package by.vsu.soa.ioay.mvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import by.vsu.soa.ioay.entity.Content;
import by.vsu.soa.ioay.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ContentsController {

    @Autowired
    private ContentService srv;

    @GetMapping("/content")
    public String content(Authentication auth, Model model) {
        model.addAttribute("contents", srv.getFileInfos(auth.getName()));
        return "contents";
    }

    @GetMapping("/download")
    public void download(@RequestParam("id") final Long id, final Authentication auth, final HttpServletResponse response) throws IOException {
        final Content info = srv.getFileInfo(id, auth.getName());

        if (info == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            // set content disposition
            final ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(info.getName(), StandardCharsets.UTF_8).build();

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-control", "private");
            if (Objects.nonNull(info.getContentType())) {
                response.setContentType(info.getContentType());
            }
            response.setContentLengthLong(info.getSize());

            srv.download(info, response.getOutputStream());
        }
    }

    @PostMapping("/content")
    public String upload(@RequestParam("content") MultipartFile content, @RequestParam(value = "desc", required = false) String desc, Authentication auth) {
        srv.upload(content, desc, auth.getName());
        return "redirect:/content";
    }

    @PostMapping(value = "/content", headers = {"X-Requested-With"})
    @ResponseBody
    public String upload2(
            @RequestParam("content") MultipartFile content,
            @RequestParam(value = "desc", required = false) String desc,
            @Header("X-Requested-With") String header,
            Authentication auth) {
        srv.upload(content, desc, auth.getName());
        return "OK";
    }
} // class
