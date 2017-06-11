package uk.ac.ebi.subs.rulebook.model.documents;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.Applicability;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class RuleSetDocument {

    private String name;
    private String description;
    private Applicability applicability;

    private List<RuleGroupDocument> ruleGroups = new ArrayList<>();
}
