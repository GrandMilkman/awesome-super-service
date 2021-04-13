package by.vsu.soa.ioay.mvc.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.vsu.soa.ioay.mvc.InfoController;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InfoDocxView extends AbstractDocxView {

    public InfoDocxView(@Value("${template.info.docx:classpath:/docx/info}") String url) {
        super();
        setUrl(url);
    }

    @Override
    protected void buildDocxDocument(final Map<String, Object> model, final XWPFDocument document, final HttpServletRequest req,
            final HttpServletResponse res) throws Exception {

        @SuppressWarnings("unchecked")
        final List<InfoController.Info> info = (List<InfoController.Info>) model.get("list");

        final XWPFTable table = document.getTableArray(0);

        for (InfoController.Info i : info) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(i.getName());
            row.getCell(1).setText(i.getValue());
        }
    }
}
