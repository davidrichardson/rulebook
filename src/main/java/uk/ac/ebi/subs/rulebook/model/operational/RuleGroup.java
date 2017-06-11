package uk.ac.ebi.subs.rulebook.model.operational;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.JsonCondition;
import uk.ac.ebi.subs.rulebook.model.RestrictionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class RuleGroup {
    private String name;
    private String description;
    private JsonCondition condition;
    private RestrictionType restrictionType;

    private List<Rule> rules = new ArrayList<>();

}
