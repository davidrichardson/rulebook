package uk.ac.ebi.subs.rulebook.validators.text;

import lombok.Data;

/**
 * Created by Dave on 10/06/2017.
 */
@Data
public class FreeTextValidatorConfig {
    private Integer minNumberOfCharacters;
    private Integer maxNumberOfCharacters;
}
