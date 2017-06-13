package uk.ac.ebi.subs.rulebook.validators.numeric;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.JsonValueLocator;

/**
 * Created by Dave on 10/06/2017.
 */
@Data
public class IntegerValidatorConfig {
    private Integer minValue;
    private Integer maxValue;
    private JsonValueLocator locator;
}
