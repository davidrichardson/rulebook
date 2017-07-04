package uk.ac.ebi.subs.rulebook.validators.numeric;

import uk.ac.ebi.subs.rulebook.validation.ValidationResult;
import uk.ac.ebi.subs.rulebook.validation.Validator;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Dave on 10/06/2017.
 */
public class IntegerValidator implements Validator {

    public IntegerValidator(IntegerValidatorConfig config) {
        this.config = config;
    }

    private IntegerValidatorConfig config;


    @Override
    public ValidationResult validate(LinkedHashMap<String,String> entity) {

        List<String> values = config.getLocator().matches(entity);

        if (values.size() > 1){
            return ValidationResult.fail();
        }

        String strValue = values.get(0);

        String outcome = "pass";

        Integer value = null;

        try {
            value = Integer.valueOf(strValue );
        }
        catch (NumberFormatException e) {
            outcome = "fail";
        }

        if (value != null){
            if (config.getMinValue() != null && value < config.getMinValue()){
                outcome = "fail";
            }

            if (config.getMaxValue() != null && value > config.getMaxValue()){
                outcome = "fail";
            }
        }


        return ValidationResult.of(outcome);
    }

    @Override
    public String getColumnName() {
        return config.getColumnNameModifier();
    }
}
