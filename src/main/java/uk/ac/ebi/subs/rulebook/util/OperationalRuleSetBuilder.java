package uk.ac.ebi.subs.rulebook.util;

import uk.ac.ebi.subs.rulebook.model.documents.RuleDocument;
import uk.ac.ebi.subs.rulebook.model.documents.RuleGroupDocument;
import uk.ac.ebi.subs.rulebook.model.documents.RuleSetDocument;
import uk.ac.ebi.subs.rulebook.model.documents.ValidationConfig;
import uk.ac.ebi.subs.rulebook.model.operational.Rule;
import uk.ac.ebi.subs.rulebook.model.operational.RuleGroup;
import uk.ac.ebi.subs.rulebook.model.operational.RuleSet;
import uk.ac.ebi.subs.rulebook.validation.Validator;
import uk.ac.ebi.subs.rulebook.validation.ValidatorFactory;

import java.util.Map;

/**
 * Created by Dave on 10/06/2017.
 */
public class OperationalRuleSetBuilder {

    private Map<String,ValidatorFactory> validatorFactoriesByType;

    public OperationalRuleSetBuilder(Map<String,ValidatorFactory> validatorFactoriesByType){
        this.validatorFactoriesByType = validatorFactoriesByType;
    }

    public RuleSet buildRuleSet(RuleSetDocument ruleSetDocument){
        RuleSet ruleSet = new RuleSet();

        ruleSet.setName( ruleSetDocument.getName() );
        ruleSet.setDescription( ruleSetDocument.getDescription() );

        for (RuleGroupDocument ruleGroupDocument : ruleSetDocument.getRuleGroups()){
            RuleGroup ruleGroup = new RuleGroup();
            ruleSet.getRuleGroups().add(ruleGroup);

            ruleGroup.setName(ruleGroupDocument.getName());
            ruleGroup.setDescription(ruleGroupDocument.getDescription());

            ruleGroup.setCondition( ruleGroupDocument.getCondition());
            ruleGroup.setRestrictionType( ruleGroupDocument.getRestrictionType());


            for (RuleDocument ruleDocument : ruleGroupDocument.getRules()){
                Rule rule = new Rule();
                ruleGroup.getRules().add(rule);

                rule.setName(ruleDocument.getName());
                rule.setDescription(ruleDocument.getDescription());
                rule.setLocatorPath(ruleDocument.getLocator());
                rule.setRequirementStrength(ruleDocument.getRequirementStrength());
                rule.setMultiplicty(ruleDocument.getMultiplicty());

                for (ValidationConfig validationConfig : ruleDocument.getValidators()){
                    ValidatorFactory vf = validatorFactoriesByType.get(  validationConfig.getType() );

                    Validator validator = vf.buildValidator(validationConfig.getConfig());

                    rule.getValidators().add(validator);
                }
            }


        }



        return ruleSet;
    }



}
