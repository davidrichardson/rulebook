package uk.ac.ebi.subs.rulebook.validators.text;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.subs.rulebook.validation.Validator;
import uk.ac.ebi.subs.rulebook.validation.ValidatorFactory;

import java.io.IOException;

/**
 * Created by davidr on 13/06/2017.
 */
public class EnumeratedValueValidatorFactory implements ValidatorFactory {

    private ObjectMapper mapper;

    @Override
    public String getValidatorTypeName() {
        return "EnumeratedValue";
    }

    @Override
    public Validator buildValidator(String configJson) {
        try {
            EnumeratedValueValidatorConfig configObject = mapper.readValue(configJson, EnumeratedValueValidatorConfig.class);
            return new EnumeratedValueValidator(configObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }
}
