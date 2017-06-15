package uk.ac.ebi.subs.rulebook.validation;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
public interface Validator {

    ValidationResult validate(LinkedHashMap<String, String> entity);

    String getColumnNameModifier();

    default List<String> validValues(){return Collections.emptyList();}
}
