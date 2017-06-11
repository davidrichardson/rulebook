package uk.ac.ebi.subs.rulebook;

import org.junit.Test;
import uk.ac.ebi.subs.rulebook.util.ValidatorFactoryLoader;
import uk.ac.ebi.subs.rulebook.validation.ValidationResult;
import uk.ac.ebi.subs.rulebook.validation.Validator;
import uk.ac.ebi.subs.rulebook.validation.ValidatorFactory;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Dave on 09/06/2017.
 */
public class TestLoadingByReflection {

    @Test
    public void getValidatoryFactoryReflection(){
        ValidatorFactoryLoader loader = new ValidatorFactoryLoader();
        Map<String,ValidatorFactory> namedFactories = loader.getValidatorFactories();

        assertThat(namedFactories.size(),greaterThanOrEqualTo(1));
        assertThat(namedFactories.containsKey("reflection load test validator"),is(true));

        ValidatorFactory factory = namedFactories.get("reflection load test validator");
        assertThat(factory.getClass(),equalTo(TestValidatorFactory.class));


    }

}
