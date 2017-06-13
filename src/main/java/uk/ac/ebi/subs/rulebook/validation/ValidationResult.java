package uk.ac.ebi.subs.rulebook.validation;

import lombok.Data;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class ValidationResult {

    public static ValidationResult fail() {
        return ValidationResult.of("fail");
    }

    public static ValidationResult pass() {
        return ValidationResult.of("pass");
    }


    public static ValidationResult of(String outcome){
        ValidationResult vr = new ValidationResult();
        vr.outcome = outcome;
        return vr;
    }

    private String outcome;
}
