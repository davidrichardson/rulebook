package uk.ac.ebi.subs.rulebook.model.operational;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.JsonPath;
import uk.ac.ebi.subs.rulebook.model.Multiplicty;
import uk.ac.ebi.subs.rulebook.model.RequirementStrength;
import uk.ac.ebi.subs.rulebook.validation.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class Rule {
    private String name;
    private String description;
    private JsonPath locatorPath;
    private RequirementStrength requirementStrength;
    private Multiplicty multiplicty;

    private List<Validator> validators = new ArrayList<>();

}
