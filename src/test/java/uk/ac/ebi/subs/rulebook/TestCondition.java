package uk.ac.ebi.subs.rulebook;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.subs.rulebook.model.JsonCondition;
import uk.ac.ebi.subs.rulebook.model.JsonObjectLocator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by davidr on 15/06/2017.
 */
public class TestCondition {

    private String document;

    @Test
    public void testMatch(){
        JsonCondition condition = new JsonCondition();
        condition.setLocator(JsonObjectLocator.of("$.attributes[?(@.name == 'role' && @.value == 'warship')]"));


        boolean match = condition.match(document);

        assertThat(match,equalTo(true));
    }

    @Test
    public void testNotMatchOneAttribute(){
        JsonCondition condition = new JsonCondition();
        condition.setLocator(JsonObjectLocator.of("$.spong"));


        boolean match = condition.match(document);

        assertThat(match,equalTo(false));
    }

    @Before
    public void buildUp() throws URISyntaxException, IOException {

        URL docUrl = this.getClass().getResource("/data/spaceship.entity.json");

        Scanner scanner = new Scanner(docUrl.openStream(), "UTF-8");
        StringBuilder buffer = new StringBuilder();

        while(scanner.hasNext())
            buffer.append(scanner.next());

        document = buffer.toString();
    }
}
