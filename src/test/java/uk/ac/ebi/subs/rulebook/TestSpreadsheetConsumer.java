package uk.ac.ebi.subs.rulebook;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.subs.rulebook.model.operational.RuleSet;
import uk.ac.ebi.subs.rulebook.util.OperationalRuleSetBuilder;
import uk.ac.ebi.subs.rulebook.util.RuleSetDocumentLoader;
import uk.ac.ebi.subs.rulebook.util.ValidatorFactoryLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by davidr on 03/07/2017.
 */
public class TestSpreadsheetConsumer {


    private RuleSet ruleSet;
    private DocumentContext expectedSpaceship;
    private Workbook workbook;
    private SpreadsheetConsumer spreadsheetConsumer;


    @Before
    public void buildUp() throws URISyntaxException, IOException {
        ruleSet = getRuleSet();
        workbook = getWorkbook();
        expectedSpaceship = JsonPath.parse(getExpectedDocument());

        spreadsheetConsumer = new SpreadsheetConsumer();
    }

    @Test
    public void testColumnAssignment() {


        List<SpreadsheetConsumer.ColumnValidators> columnValidators = spreadsheetConsumer
                .columnsToValidators(ruleSet, workbook.getSheetAt(0).getRow(0));


        List<String> columnRuleNames = columnValidators.stream().map(cv -> cv == null ? "" : cv.getRule().getName()).collect(Collectors.toList());

        List<String> expexctedColumnRuleNames = Arrays.asList(
                "serialNumber",
                "class",
                "role",
                "crew capacity",
                "crew capacity", //units
                "passenger capacity",
                "passenger capacity", //units
                "weapon",
                "weapon",
                "carrying ship",
                "carrying ship"
        );

        assertThat(columnRuleNames,equalTo(expexctedColumnRuleNames));
    }

    @Test
    public void testSpreadsheetConsumer() {

        List<String> hopefullySpaceships = spreadsheetConsumer.convertSpreadsheetToDocuments(ruleSet, workbook);

        assertThat(hopefullySpaceships, hasSize(1));

        String spaceship = hopefullySpaceships.get(0);

        assertThat(spaceship, notNullValue());

        DocumentContext jsonSpaceShip = JsonPath.parse(spaceship);

        assertThat(jsonSpaceShip, equalTo(expectedSpaceship));
    }

    private RuleSet getRuleSet() throws URISyntaxException {
        RuleSetDocumentLoader loader = new RuleSetDocumentLoader();

        URL ruleUrl = this.getClass().getResource("/rules/space_ships.rules.json");
        File jsonFilePath = Paths.get(ruleUrl.toURI()).toFile();

        ValidatorFactoryLoader vfLoader = new ValidatorFactoryLoader();
        OperationalRuleSetBuilder builder = new OperationalRuleSetBuilder(vfLoader.getValidatorFactories());

        return builder.buildRuleSet(loader.load(jsonFilePath));
    }

    private HSSFWorkbook getWorkbook() throws IOException {
        URL workbookUrl = this.getClass().getResource("/data/spaceship.entities.xls");

        InputStream stream = workbookUrl.openStream();

        HSSFWorkbook workbook = new HSSFWorkbook(stream);

        return workbook;
    }

    private String getExpectedDocument() throws IOException {
        return TestHelper.loadStringFromResource("/data/spaceship.entity.json");
    }

}
