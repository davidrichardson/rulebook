package uk.ac.ebi.subs.rulebook;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.subs.rulebook.model.documents.RuleSetDocument;
import uk.ac.ebi.subs.rulebook.model.operational.RuleSet;
import uk.ac.ebi.subs.rulebook.util.OperationalRuleSetBuilder;
import uk.ac.ebi.subs.rulebook.util.RuleSetDocumentLoader;
import uk.ac.ebi.subs.rulebook.util.ValidatorFactoryLoader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


/**
 * Created by davidr on 15/06/2017.
 */
public class TestTemplate {

    private RuleSet ruleSet;
    private File file = new File("templateTest.out.xls");
    private TemplateBuilder templateBuilder = new TemplateBuilder();


    @Test
    public void testTemplateWrite() throws IOException {
        file.delete();


        templateBuilder.writeTemplate(file,ruleSet);


        assertThat(file.exists(),equalTo(true));
        assertThat(file.length(),greaterThan(1l));
    }


    @Before
    public void buildUp() throws URISyntaxException {
        ruleSet();
    }

    private void ruleSet() throws URISyntaxException {
        ValidatorFactoryLoader vfLoader = new ValidatorFactoryLoader();

        RuleSetDocumentLoader loader = new RuleSetDocumentLoader();
        OperationalRuleSetBuilder builder = new OperationalRuleSetBuilder(vfLoader.getValidatorFactories());

        URL ruleUrl = this.getClass().getResource("/rules/space_ships.rules.json");
        File jsonFilePath = Paths.get(ruleUrl.toURI()).toFile();

        RuleSetDocument ruleSetDocument = loader.load(jsonFilePath);

        ruleSet = builder.buildRuleSet(ruleSetDocument);
    }
}
