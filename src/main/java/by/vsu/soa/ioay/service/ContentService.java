package by.vsu.soa.ioay.service;

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import by.vsu.soa.ioay.entity.Content;

public interface ContentService {

    Content getFileInfo(Long id);

    Content getFileInfo(Long id, String username);

    List<Content> getFileInfos(String username);

    void upload(MultipartFile file, String desc, String username);

    void download(Content info, OutputStream os);
}
