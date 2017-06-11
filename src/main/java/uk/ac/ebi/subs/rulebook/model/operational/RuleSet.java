package uk.ac.ebi.subs.rulebook.model.operational;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.Applicability;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class RuleSet {
    private String name;
    private String description;

    private List<RuleGroup> ruleGroups = new ArrayList<>();
}
