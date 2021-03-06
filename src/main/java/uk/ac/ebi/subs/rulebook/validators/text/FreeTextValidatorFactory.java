package uk.ac.ebi.subs.rulebook.validators.text;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.subs.rulebook.validation.Validator;
import uk.ac.ebi.subs.rulebook.validation.ValidatorFactory;

import java.io.IOException;

/**
 * Created by Dave on 10/06/2017.
 */
public class FreeTextValidatorFactory implements ValidatorFactory{

    private ObjectMapper mapper;

    @Override
    public void setObjectMapper(ObjectMapper objectMapper) {
        mapper = objectMapper;
    }

    @Override
    public String getValidatorTypeName() {
        return "FreeText";
    }

    @Override
    public Validator buildValidator(String configJson) {
        try {
            FreeTextValidatorConfig configObject = mapper.readValue(configJson,FreeTextValidatorConfig.class);
            return new FreeTextValueValidator(configObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
