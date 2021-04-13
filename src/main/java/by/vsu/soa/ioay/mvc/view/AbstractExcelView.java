package by.vsu.soa.ioay.mvc.view;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

public abstract class AbstractExcelView extends AbstractTemplateView {

    /**The content type for an Excel response.*/
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**The extension to look for existing templates.*/
    private static final String EXTENSION = ".xlsx";

    protected static final String FILENAME = "_filename_";

    public AbstractExcelView() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    public boolean checkResource(final Locale locale) throws Exception {
        return getApplicationContext().getResource(getUrl()).exists();
    }

    @Override
    protected void renderMergedTemplateModel(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Resource resource = getTemplateResource(getUrl(), request);

        final XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());

        buildDocunent(model, workbook, request, response);

        response.setContentType(getContentType());

        // get file name
        final String filename;

        if (model.containsKey(FILENAME)) {
            filename = model.get(FILENAME) + EXTENSION;
        } else {
            filename = resource.getFilename();
        }

        // set content disposition
        final ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(filename, StandardCharsets.UTF_8).build();

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        // Flush byte array to servlet output stream.
        final ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        workbook.close();
    }

    protected abstract void buildDocunent(
            Map<String, Object> model,
            XSSFWorkbook workbook,
            HttpServletRequest req,
            HttpServletResponse res) throws Exception;

    protected Resource getTemplateResource(final String location, final HttpServletRequest request) {
        final Resource resource;

        if (location.lastIndexOf(EXTENSION) == -1) {
            final LocalizedResourceHelper helper = new LocalizedResourceHelper(getApplicationContext());
            final Locale locale = RequestContextUtils.getLocale(request);
            resource = helper.findLocalizedResource(location, EXTENSION, locale);
        } else {
            resource = getApplicationContext().getResource(location);
        }

        return resource;
    }

    protected XSSFCell getCell(final XSSFSheet sheet, final int rownum, final int cellnum) {
        return getCell(sheet, rownum, cellnum, CellType.STRING);
    }

    protected XSSFCell getCell(final XSSFSheet sheet, final int rownum, final int cellnum, final CellType type) {
        XSSFRow row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }
        XSSFCell cell = row.getCell(cellnum);
        if (cell == null) {
            cell = row.createCell(cellnum, type);
        }
        return cell;
    }
}
