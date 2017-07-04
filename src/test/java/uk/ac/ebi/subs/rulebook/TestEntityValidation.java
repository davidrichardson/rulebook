package uk.ac.ebi.subs.rulebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.subs.rulebook.model.documents.RuleSetDocument;
import uk.ac.ebi.subs.rulebook.model.operational.RuleSet;
import uk.ac.ebi.subs.rulebook.util.OperationalRuleSetBuilder;
import uk.ac.ebi.subs.rulebook.util.RuleSetDocumentLoader;
import uk.ac.ebi.subs.rulebook.util.ValidatorFactoryLoader;
import uk.ac.ebi.subs.rulebook.validation.ValidationResult;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Created by davidr on 13/06/2017.
 */
public class TestEntityValidation {

    private RuleSet ruleSet;
    private String document;
    private EntityValidator entityValidator = new EntityValidator(new ObjectMapper());

    @Before
    public void buildUp() throws URISyntaxException, IOException {
        ruleSet();
        document();
    }

    @Test
    public void testValidation(){

        Collection<ValidationResult> results = entityValidator.validationResults(document,ruleSet);

        System.out.println(results);

    }


    private void document() throws IOException {
        document = TestHelper.loadStringFromResource("/data/spaceship.entity.json");
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
