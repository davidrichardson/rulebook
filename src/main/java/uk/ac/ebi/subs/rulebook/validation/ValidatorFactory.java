package uk.ac.ebi.subs.rulebook.validation;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Dave on 09/06/2017.
 */
public interface ValidatorFactory {

    public String getValidatorTypeName();
    public Validator buildValidator(String config);
    public void setObjectMapper(ObjectMapper objectMapper);


}
