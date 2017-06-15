package uk.ac.ebi.subs.rulebook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import uk.ac.ebi.subs.rulebook.model.operational.Rule;
import uk.ac.ebi.subs.rulebook.model.operational.RuleGroup;
import uk.ac.ebi.subs.rulebook.model.operational.RuleSet;
import uk.ac.ebi.subs.rulebook.validation.Validator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by davidr on 15/06/2017.
 */
public class TemplateBuilder {

    public void writeTemplate(File file, RuleSet ruleSet) throws IOException {
        Workbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("Template");

        // Create a row and put some cells in it. Rows are 0 based.
        Row headerRow = sheet.createRow((short)0);

        int cellPos = 0;

        for (RuleGroup ruleGroup : ruleSet.getRuleGroups()){
            for (Rule rule : ruleGroup.getRules()){
                for (Validator validator : rule.getValidators()) {

                    String columnName = rule.getName() + validator.getColumnNameModifier();

                    Cell cell = headerRow.createCell(cellPos);
                    cell.setCellValue(columnName);

                    cellPos++;
                }
            }
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(file);
        wb.write(fileOut);
        fileOut.close();
    }


}
