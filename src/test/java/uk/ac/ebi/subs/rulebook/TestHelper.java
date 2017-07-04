package uk.ac.ebi.subs.rulebook;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by davidr on 03/07/2017.
 */
public class TestHelper {

    public static String loadStringFromResource(String resource) throws IOException {
        URL docUrl = TestHelper.class.getResource(resource);

        Scanner scanner = new Scanner(docUrl.openStream(), "UTF-8");
        StringBuilder buffer = new StringBuilder();

        while(scanner.hasNext())
            buffer.append(scanner.next());

        return buffer.toString();
    }
}
