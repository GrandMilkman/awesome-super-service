package by.vsu.soa.ioay.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import by.vsu.soa.ioay.service.ContentService;

@SpringBootTest
public class ContentServiceTest {

    @Autowired
    private ContentService srv;

    @Test
    public void getFileInfoNull() {
        assertNull(srv.getFileInfo(1L));
    }

    @Test
    public void getFileInfoNull2() {
        assertNull(srv.getFileInfo(1L, "admin"));
    }

    @Test
    public void getFileInfosEmptyList() {
        assertTrue(srv.getFileInfos("admin").isEmpty());
    }

    @Test
    public void uploadDoesNotThrowException() {
        MultipartFile file = new MockMultipartFile("content", "text1.txt", "text/plain", "simple text".getBytes());
        assertDoesNotThrow(()-> srv.upload(file, "desc by foo", "foo"));
    }

    @Test
    public void uploadThrowsNullPointerException() {
        MultipartFile file = new MockMultipartFile("content", "text2.txt", "text/plain", "text from unknown".getBytes());
        assertThrows(NullPointerException.class, ()-> srv.upload(file, "desc by ?", ""));
    }
}
