package uk.ac.ebi.subs.rulebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.subs.rulebook.validation.ValidationResult;
import uk.ac.ebi.subs.rulebook.validation.Validator;
import uk.ac.ebi.subs.rulebook.validation.ValidatorFactory;

/**
 * Created by Dave on 10/06/2017.
 */
public class TestValidatorFactory implements ValidatorFactory {

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
            public ValidationResult validate(String entity) {
                return new ValidationResult();
            }
        };
    }
}