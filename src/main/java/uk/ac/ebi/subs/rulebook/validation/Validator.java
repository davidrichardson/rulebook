package uk.ac.ebi.subs.rulebook.validation;

import uk.ac.ebi.subs.rulebook.model.JsonValueLocator;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
public interface Validator {

    ValidationResult validate(LinkedHashMap<String, String> entity);

    String getColumnName();

    default List<String> validValues(){return Collections.emptyList();}

}
