package uk.ac.ebi.subs.rulebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.subs.rulebook.validation.ValidationResult;
import uk.ac.ebi.subs.rulebook.validation.Validator;
import uk.ac.ebi.subs.rulebook.validation.ValidatorFactory;

import java.util.LinkedHashMap;

/**
 * Created by Dave on 10/06/2017.
 */
public class TestValidatorFactoryImpl implements ValidatorFactory {

    @Override
    public void setObjectMapper(ObjectMapper objectMapper) {
        //
    }

    @Override
    public String getValidatorTypeName() {
        return "reflection load test validator";
    }

    @Override
    public Validator buildValidator(String config) {
        return new Validator() {
            @Override
            public ValidationResult validate(LinkedHashMap<String,String> entity) {
                return new ValidationResult();
            }

            @Override
            public String getColumnNameModifier() {
                return "";
            }
        };
    }
}