package uk.ac.ebi.subs.rulebook;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import uk.ac.ebi.subs.rulebook.model.operational.Rule;
import uk.ac.ebi.subs.rulebook.model.operational.RuleSet;
import uk.ac.ebi.subs.rulebook.validation.Validator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by davidr on 27/06/2017.
 */
public class SpreadsheetConsumer {

    public List<String> convertSpreadsheetToDocuments(RuleSet ruleSet, Workbook workbook) {
        List<String> documents = new LinkedList<>();


        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            documents.addAll(
                    this.convertSpreadsheetToDocuments(ruleSet, workbook.getSheetAt(i))
            );
        }

        return documents;
    }

    public List<String> convertSpreadsheetToDocuments(RuleSet ruleSet, Sheet spreadsheet) {

        List<String> documents = new LinkedList<>();

        List<ColumnValidators> columnsToValidators = null;

        for (Row row : spreadsheet) {
            if (row.getCell(0).getStringCellValue().startsWith("#")) {
                //this is a comment row, ignore
                continue;
            }

            if (columnsToValidators == null) {
                columnsToValidators = columnsToValidators(ruleSet, row);
                continue;
            }

            String document = buildDocument(columnsToValidators, row);

            if (document != null) {
                documents.add(document);
            }
        }

        return documents;
    }

    private String buildDocument(List<ColumnValidators> columnsToValidators, Row row) {
        DocumentContext documentContext = JsonPath.parse("{}");

        for (int i = 0; i < columnsToValidators.size(); i++) {

            ColumnValidators columnValidators = columnsToValidators.get(i);

            if (columnValidators == null) {
                continue;
            }


            String cellValue = row.getCell(i).getStringCellValue();

            List<String> paths = new ArrayList<>();

            paths.add(columnValidators.getRule().getLocatorPath().getPath());




//            if (columnValidators.getRule().getLocatorPath().getPath())

            documentContext.set(columnValidators.getRule().getLocatorPath().getPath(), cellValue);
        }

        return documentContext.jsonString();
    }

    public List<ColumnValidators> columnsToValidators(RuleSet ruleSet, Row headerRow) {

        Map<String, Rule> namesToRules = namesToRules(ruleSet);


        ArrayList<ColumnValidators> columnValidators = new ArrayList<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            columnValidators.add(null);
        }

        List<Cell> headerCellBuffer = new ArrayList<>();

        List<Cell> cells = StreamSupport.stream(headerRow.spliterator(), false).collect(Collectors.toList());
        Collections.reverse(cells);


        for (Cell cell : cells) {

            String headerColumnName = cell.getStringCellValue();

            if (namesToRules.containsKey(headerColumnName)) {
                Rule ruleForColumn = namesToRules.get(headerColumnName);

                createColumnValidators(ruleForColumn, columnValidators, headerCellBuffer, cell);
                headerCellBuffer.clear();
            } else {
                headerCellBuffer.add(cell);
            }

        }

        return columnValidators;
    }

    private void createColumnValidators(Rule rule, List<ColumnValidators> columnValidators, List<Cell> headerCellBuffer, Cell cell) {

        ColumnValidators cv = new ColumnValidators(rule);

        System.out.println("Rule:" + rule.getName() + " column:" + cell.getColumnIndex());

        String columnName = cell.getStringCellValue();
        int columnIndex = cell.getColumnIndex();

        columnValidators.set(columnIndex, cv);

        rule.getValidators().stream().filter(
                validator -> validator.getColumnName() == null || validator.getColumnName().isEmpty()
        ).forEach(validator -> cv.addValidator(validator));


        for (Cell headerCell : headerCellBuffer) {
            addSubordinateColumns(rule, columnValidators, headerCell);
        }


    }

    private void addSubordinateColumns(Rule rule, List<ColumnValidators> columnValidators, Cell headerCell) {
        String columnName = headerCell.getStringCellValue();
        int columnIndex = headerCell.getColumnIndex();

        System.out.println("subordinate column:" + columnName + " column:" + columnIndex);

        ColumnValidators cv2 = new ColumnValidators(rule);
        columnValidators.set(columnIndex, cv2);

        rule.getValidators().stream().filter(
                validator -> validator.getColumnName() != null && validator.getColumnName().equals(columnName)
        ).forEach(validator -> cv2.addValidator(validator));
    }

    private Map<String, Rule> namesToRules(RuleSet ruleSet) {
        Map<String, Rule> namesToRules = new HashMap<>();

        ruleSet.getRuleGroups().stream().flatMap(rg -> rg.getRules().stream()).forEach(rule -> namesToRules.put(rule.getName(), rule));

        return namesToRules;
    }


    protected class ColumnValidators {
        private Rule rule;
        private Collection<Validator> validators = new LinkedList<>();

        public ColumnValidators(Rule rule) {
            this.rule = rule;
        }

        public Rule getRule() {
            return rule;
        }

        public Collection<Validator> getValidators() {
            return validators;
        }

        public void addValidator(Validator validator) {
            this.validators.add(validator);
        }
    }
}
