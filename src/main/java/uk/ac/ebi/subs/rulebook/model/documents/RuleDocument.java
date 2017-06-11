package uk.ac.ebi.subs.rulebook.model.documents;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.JsonPath;
import uk.ac.ebi.subs.rulebook.model.Multiplicty;
import uk.ac.ebi.subs.rulebook.model.RequirementStrength;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class RuleDocument {

    private String name;
    private String description;
    private JsonPath locatorPath;
    private RequirementStrength requirementStrength;
    private Multiplicty multiplicty;

    private List<ValidationConfig> validators = new ArrayList<>();

}
