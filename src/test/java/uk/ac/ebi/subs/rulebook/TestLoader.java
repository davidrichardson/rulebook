package uk.ac.ebi.subs.rulebook;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.subs.rulebook.model.RestrictionType;
import uk.ac.ebi.subs.rulebook.model.documents.RuleSetDocument;
import uk.ac.ebi.subs.rulebook.model.operational.RuleGroup;
import uk.ac.ebi.subs.rulebook.model.operational.RuleSet;
import uk.ac.ebi.subs.rulebook.util.OperationalRuleSetBuilder;
import uk.ac.ebi.subs.rulebook.util.RuleSetDocumentLoader;
import uk.ac.ebi.subs.rulebook.util.ValidatorFactoryLoader;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


/**
 * Created by Dave on 09/06/2017.
 */

public class TestLoader {

    private RuleSetDocumentLoader loader;
    private OperationalRuleSetBuilder builder;

    @Before
    public void buildUp() {
        ValidatorFactoryLoader vfLoader = new ValidatorFactoryLoader();

        loader = new RuleSetDocumentLoader();
        builder = new OperationalRuleSetBuilder(vfLoader.getValidatorFactories());
    }

    @Test
    public void testLoad() throws URISyntaxException {
        RuleSetDocument ruleSet = getRuleSetDocument();

        assertThat(ruleSet, notNullValue());
        assertThat(ruleSet.getName(), equalTo("spacecraft checklist"));
    }

    private RuleSetDocument getRuleSetDocument() throws URISyntaxException {
        URL ruleUrl = this.getClass().getResource("/rules/space_ships.rules.json");
        File jsonFilePath = Paths.get(ruleUrl.toURI()).toFile();

        return loader.load(jsonFilePath);
    }

    @Test
    public void testConvert() throws URISyntaxException {
        RuleSetDocument doc = getRuleSetDocument();

        RuleSet spaceShipRuleSet = builder.buildRuleSet(doc);

        assertThat(spaceShipRuleSet,notNullValue());
        assertThat(spaceShipRuleSet.getName(),equalTo("spacecraft checklist"));
        assertThat(spaceShipRuleSet.getDescription(),equalTo("a geeky test checklist"));
        assertThat(spaceShipRuleSet.getRuleGroups(),hasSize(1));

        RuleGroup ruleGroup = spaceShipRuleSet.getRuleGroups().get(0);

        assertThat(ruleGroup.getName(),equalTo("common core rules"));
        assertThat(ruleGroup.getDescription(),nullValue());
        assertThat(ruleGroup.getRestrictionType(),equalTo(RestrictionType.any));

    }

}
