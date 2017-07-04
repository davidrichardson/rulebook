package uk.ac.ebi.subs.rulebook.validators.text;

import uk.ac.ebi.subs.rulebook.validation.ValidationResult;
import uk.ac.ebi.subs.rulebook.validation.Validator;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by davidr on 13/06/2017.
 */
public class EnumeratedValueValidator implements Validator {


    public EnumeratedValueValidator(EnumeratedValueValidatorConfig config) {
        this.config = config;
    }

    private EnumeratedValueValidatorConfig config;

    @Override
    public ValidationResult validate(LinkedHashMap<String, String> entity) {
        List<String> matches = config.getLocator().matches(entity);

        if (matches.size() != 1){
            return ValidationResult.fail();
        }

        String value = matches.get(0);

        if (config.getPermittedValues().contains(value)){
            return ValidationResult.pass();
        }

        return ValidationResult.fail();
    }

    @Override
    public List<String> validValues() {
        return new LinkedList<>(config.getPermittedValues());
    }

    @Override
    public String getColumnName() {
        return config.getColumnNameModifier();
    }
}
