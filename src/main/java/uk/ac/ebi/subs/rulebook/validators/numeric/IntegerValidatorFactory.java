package uk.ac.ebi.subs.rulebook.validators.numeric;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.subs.rulebook.validation.Validator;
import uk.ac.ebi.subs.rulebook.validation.ValidatorFactory;

import java.io.IOException;

/**
 * Created by Dave on 10/06/2017.
 */
public class IntegerValidatorFactory implements ValidatorFactory{

    private ObjectMapper mapper;

    @Override
    public void setObjectMapper(ObjectMapper objectMapper) {
        mapper = objectMapper;
    }

    @Override
    public String getValidatorTypeName() {
        return "Integer";
    }

    @Override
    public Validator buildValidator(String configJson) {
        try {
            IntegerValidatorConfig configObject = mapper.readValue(configJson,IntegerValidatorConfig.class);
            return new IntegerValidator(configObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }





    }

}
