package by.vsu.soa.ioay.mvc.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.vsu.soa.ioay.entity.User;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserListExelView extends AbstractExcelView {

    public UserListExelView(@Value("${template.list.users.exel:classpath:/xlsx/users}") String url) {
        super();
        setUrl(url);
    }

    @Override
    protected void buildDocunent(final Map<String, Object> model, final XSSFWorkbook workbook, final HttpServletRequest req, final HttpServletResponse res) throws Exception {

        @SuppressWarnings("unchecked")
        final List<User> users = (List<User>) model.get("users");

        final XSSFSheet sheet = workbook.getSheetAt(0);

        // get cells style
        final XSSFCellStyle style1 = getCell(sheet, 1, 0).getCellStyle();
        final XSSFCellStyle style2 = getCell(sheet, 1, 1).getCellStyle();

        for (int i = 0; i < users.size(); i++) {
            XSSFCell cell = getCell(sheet, i + 1, 0);
            cell.setCellStyle(style1);
            cell.setCellValue(i + 1);
            cell = getCell(sheet, i + 1, 1);
            cell.setCellStyle(style2);
            cell.setCellValue(users.get(i).getName());
        }
    }
}
