package uk.ac.ebi.subs.rulebook.validators.text;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.JsonValueLocator;

/**
 * Created by Dave on 10/06/2017.
 */
@Data
public class FreeTextValidatorConfig {
    private Integer minNumberOfCharacters;
    private Integer maxNumberOfCharacters;
    private JsonValueLocator locator = JsonValueLocator.of("$.value");
    private String columnNameModifier = "";
}
