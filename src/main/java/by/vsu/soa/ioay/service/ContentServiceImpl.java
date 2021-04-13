package by.vsu.soa.ioay.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import by.vsu.soa.ioay.dao.ContentDao;
import by.vsu.soa.ioay.entity.Content;
import by.vsu.soa.ioay.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContentServiceImpl implements ContentService, ApplicationListener<ContextRefreshedEvent> {

    @Value("${dir.repo:.repo}")
    private String dirRepo;

    @Autowired
    private ContentDao dao;

    @Autowired
    private UserService srv;

    @Override
    public Content getFileInfo(Long id) {
        return dao.read(id);
    }

    @Override
    public List<Content> getFileInfos(String username) {
        return dao.findContentsByUserName(username);
    }

    @Override
    public Content getFileInfo(Long id, String username) {
        return dao.findContent(id, username);
    }

    @Transactional
    @Override
    public void upload(MultipartFile file, String desc, String username) {
        User user = srv.getUser(username);

        Content info = new Content();

        info.setName(file.getOriginalFilename());
        info.setSize(file.getSize());
        info.setContentType(file.getContentType());
        info.setDesc(desc);
        info.setOwnerId(user.getId());

        dao.create(info);

        try {
            File dir = Paths.get(dirRepo, user.getName()).toFile();
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.transferTo(Paths.get(dir.getAbsolutePath(), "file." + info.getId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(Content info, OutputStream os) {
        try {
            FileCopyUtils.copy(Files.newInputStream(Paths.get(dirRepo, srv.getUser(info.getOwnerId()).getName(),  "file." + info.getId())), os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final File repo = new File(dirRepo);
        if (!repo.exists()) {
            repo.mkdir();
        }
    }
}
