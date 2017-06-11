package uk.ac.ebi.subs.rulebook.validators.text;

import uk.ac.ebi.subs.rulebook.validation.ValidationResult;
import uk.ac.ebi.subs.rulebook.validation.Validator;

/**
 * Created by Dave on 10/06/2017.
 */
public class FreeTextValidator implements Validator {

    public FreeTextValidator(FreeTextValidatorConfig config) {
        this.minNumberOfCharacters = config.getMinNumberOfCharacters();
        this.maxNumberOfCharacters = config.getMaxNumberOfCharacters();
    }

    private Integer minNumberOfCharacters;
    private Integer maxNumberOfCharacters;

    @Override
    public ValidationResult validate(String entity) {
        String outcome;

        if (entity.length() < minNumberOfCharacters || entity.length() > maxNumberOfCharacters) {
            outcome = "fail";
        } else {
            outcome = "pass";
        }

        return ValidationResult.of(outcome);
    }
}
