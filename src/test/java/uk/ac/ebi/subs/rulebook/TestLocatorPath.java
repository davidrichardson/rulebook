package uk.ac.ebi.subs.rulebook;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.subs.rulebook.model.JsonObjectLocator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
/**
 * Created by davidr on 12/06/2017.
 */
public class TestLocatorPath {

    private String document;

    @Test
    public void testMatchAttributeList(){
        JsonObjectLocator path = JsonObjectLocator.of("$.attributes[*]");

        List<LinkedHashMap<String,String>> matches = path.matches(document);

        assertThat(matches,hasSize(8));
        for(LinkedHashMap match : matches) {


            System.out.println(match);
        }
    }


    @Test
    public void testMatchOneProperty(){
        JsonObjectLocator path = JsonObjectLocator.of("$.serialNumber");

        List<LinkedHashMap<String,String>> matches = path.matches(document);

        assertThat(matches,hasSize(1));
        for(LinkedHashMap match : matches) {


            System.out.println(match);
        }
    }

    @Test
    public void testMatchOneAttribute(){
        JsonObjectLocator path = JsonObjectLocator.of("$.attributes[?(@.name == 'role')]");

        List<LinkedHashMap<String,String>> matches = path.matches(document);

        assertThat(matches,hasSize(1));
        for(LinkedHashMap match : matches) {


            System.out.println(match);
        }
    }

    @Before
   public void buildUp() throws URISyntaxException, IOException {
        document = TestHelper.loadStringFromResource("/data/spaceship.entity.json");
    }
}
