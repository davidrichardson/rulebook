package uk.ac.ebi.subs.rulebook.model;

import lombok.Data;

/**
 * Created by Dave on 09/06/2017.
 */
@Data(staticConstructor = "of")
public class JsonPath {

    private String path;
}
