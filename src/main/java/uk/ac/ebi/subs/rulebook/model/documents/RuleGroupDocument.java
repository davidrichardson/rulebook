package uk.ac.ebi.subs.rulebook.model.documents;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.JsonCondition;
import uk.ac.ebi.subs.rulebook.model.RestrictionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class RuleGroupDocument {

    private String name;
    private String description;
    private JsonCondition condition;
    private RestrictionType restrictionType;

    private List<RuleDocument> rules = new ArrayList<>();
}
