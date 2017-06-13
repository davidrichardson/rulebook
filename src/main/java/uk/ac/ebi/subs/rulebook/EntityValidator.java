package uk.ac.ebi.subs.rulebook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import uk.ac.ebi.subs.rulebook.model.Multiplicty;
import uk.ac.ebi.subs.rulebook.model.RequirementStrength;
import uk.ac.ebi.subs.rulebook.model.operational.Rule;
import uk.ac.ebi.subs.rulebook.model.operational.RuleGroup;
import uk.ac.ebi.subs.rulebook.model.operational.RuleSet;
import uk.ac.ebi.subs.rulebook.validation.ValidationResult;
import uk.ac.ebi.subs.rulebook.validation.Validator;

import java.util.*;

/**
 * Created by Dave on 10/06/2017.
 */
public class EntityValidator {

    private ObjectMapper objectMapper;

    public EntityValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Collection<ValidationResult> validateEntity(Object entity, RuleSet ruleSet) {

        DocumentContext documentContext = JsonPath.parse(entity);

        return this.validateEntity(documentContext,ruleSet);
    }

    public Collection<ValidationResult> validateEntity(DocumentContext documentContext, RuleSet ruleSet) {
        return this.validateEntity(documentContext.jsonString(),ruleSet);
    }

    public Collection<ValidationResult> validationResults(String document, RuleSet ruleSet){
        List<ValidationResult> results = new LinkedList<>();

        for (RuleGroup ruleGroup : ruleSet.getRuleGroups()){

            //TODO check condition of rule group before invoking
            //TODO tally up number of matches against Restriction type
            for (Rule rule : ruleGroup.getRules()){

                results.addAll(
                        validateForRule(document,rule)
                );

            }

        }

        return results;
    }

    private List<ValidationResult> validateForRule(String document, Rule rule) {
        List<ValidationResult> ruleResults = new LinkedList<>();

        List<LinkedHashMap<String,String>> matches =  rule.getLocatorPath().matches(document);

        if (rule.getMultiplicty().equals(Multiplicty.one) &&
                matches.size() > 1){
            ruleResults.add(ValidationResult.fail());
        }

        if (rule.getRequirementStrength().equals(RequirementStrength.mandatory) && matches.size() < 1){
            ruleResults.add(ValidationResult.fail());
        }

        for (LinkedHashMap<String,String> match : matches){
            for(Validator validator : rule.getValidators()){
                ruleResults.add(validator.validate(match));
            }
        }

        return ruleResults;
    }
}
