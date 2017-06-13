package uk.ac.ebi.subs.rulebook.model.documents;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.JsonValueLocator;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class ValidationConfig {

    private String type;

    @JsonRawValue
    private String config;


}
