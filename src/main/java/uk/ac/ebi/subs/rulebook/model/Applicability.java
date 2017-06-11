package uk.ac.ebi.subs.rulebook.model;

import lombok.Data;
import uk.ac.ebi.subs.rulebook.model.documents.ValidationConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class Applicability {

    private List<ValidationConfig> config = new ArrayList<>();
}
