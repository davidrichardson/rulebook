package uk.ac.ebi.subs.rulebook.validators.text;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.JsonValueLocator;

import java.util.Set;

/**
 * Created by davidr on 13/06/2017.
 */
@Data
public class EnumeratedValueValidatorConfig {

    private Set<String> permittedValues;
    private JsonValueLocator locator = JsonValueLocator.of("$.value");
}
