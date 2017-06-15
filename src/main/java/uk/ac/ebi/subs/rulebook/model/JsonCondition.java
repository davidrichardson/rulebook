package uk.ac.ebi.subs.rulebook.model;

import com.jayway.jsonpath.PathNotFoundException;
import lombok.Data;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class JsonCondition {

    private JsonObjectLocator locator;

    public boolean match(String document){
        try{
            return !locator.matches(document).isEmpty();
        }
        catch (PathNotFoundException e){
            return false;
        }
    }

}
