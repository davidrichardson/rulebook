package uk.ac.ebi.subs.rulebook.validation;

/**
 * Created by Dave on 09/06/2017.
 */
public interface Validator {
    public ValidationResult validate(String entity);
}
