package by.vsu.soa.ioay.mvc.view;

import java.nio.charset.StandardCharsets;

//import static com.epam.charity.kwl.util.ContentUtils.encodeContentDisposition;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

public abstract class AbstractDocxView extends AbstractTemplateView {

    /**The content type for an Excel response.*/
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    /**The extension to look for existing templates.*/
    private static final String EXTENSION = ".docx";

    protected static final String FILENAME = "filename";

    public AbstractDocxView() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    public boolean checkResource(final Locale locale) {
        return getApplicationContext().getResource(getUrl()).exists();
    }

    @Override
    protected void renderMergedTemplateModel(final Map<String, Object> model, final HttpServletRequest req,
            final HttpServletResponse res) throws Exception {

        final Resource resource = getTemplateResource(getUrl(), req);

        final XWPFDocument document = new XWPFDocument(resource.getInputStream());

        buildDocxDocument(model, document, req, res);

        // Set the content type.
        res.setContentType(getContentType());

        String filename;

        if (model.containsKey(FILENAME)) {
            filename = model.get(FILENAME) + EXTENSION;
        } else {
            filename = resource.getFilename();
        }

        // set content disposition
        final ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(filename, StandardCharsets.UTF_8).build();

        res.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        // Flush byte array to servlet output stream.
        final ServletOutputStream out = res.getOutputStream();
        document.write(out);
        out.flush();
        document.close();
    }

    protected Resource getTemplateResource(final String url, final HttpServletRequest req) throws Exception {
        Resource resource;

        if (url.lastIndexOf(EXTENSION) == -1) {
            final LocalizedResourceHelper helper = new LocalizedResourceHelper(getApplicationContext());
            final Locale userLocale = RequestContextUtils.getLocale(req);
            resource = helper.findLocalizedResource(url, EXTENSION, userLocale);
        } else {
            resource = getApplicationContext().getResource(url);
        }

        // Create the Resource from the source.
        if (logger.isDebugEnabled()) {
            logger.debug("Loading Resource from " + resource);
        }

        return resource;
    }

    public void replace(final XWPFDocument document, final String key, final String value) {
        for (XWPFParagraph p : document.getParagraphs()) {
            replace(p, key, value);
        }
    }

    public void replace(final XWPFParagraph paragraph, final String key, final String value) {
        List<XWPFRun> list = paragraph.getRuns();
        if (list != null) {
            for (XWPFRun r : list) {
                String text = r.getText(0);
                if (text != null && text.contains(key)) {
                    text = text.replace(key, value);
                    if (value.contains("\n")) {
                        String[] lines = value.split("\n");
                        r.setText(lines[0], 0);
                        for (int i = 1; i < lines.length; i++) {
                            r.addBreak();
                            r.setText(lines[i]);
                        }
                    } else {
                        r.setText(text, 0);
                    }
                }
            }
        }
    }

    protected abstract void buildDocxDocument(Map<String, Object> model, XWPFDocument document,
            HttpServletRequest req, HttpServletResponse res) throws Exception;
}

