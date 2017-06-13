package uk.ac.ebi.subs.rulebook.validators.text;

import uk.ac.ebi.subs.rulebook.validation.ValidationResult;
import uk.ac.ebi.subs.rulebook.validation.Validator;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Dave on 10/06/2017.
 */
public class FreeTextValueValidator implements Validator {

    public FreeTextValueValidator(FreeTextValidatorConfig config) {
        this.config = config;
    }

    private FreeTextValidatorConfig config;



    @Override
    public ValidationResult validate(LinkedHashMap<String,String> entity) {

        List<String> values = config.getLocator().matches(entity);

        if (values.size() > 1){
            return ValidationResult.fail();
        }

        String value = values.get(0);

        String outcome = "pass";

        if (config.getMinNumberOfCharacters() != null && value.length() < config.getMinNumberOfCharacters()) {
            outcome = "fail";
        }
        if (config.getMaxNumberOfCharacters() != null && value.length() > config.getMaxNumberOfCharacters()) {
            outcome = "fail";
        }

        return ValidationResult.of(outcome);
    }
}
