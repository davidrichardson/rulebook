package uk.ac.ebi.subs.rulebook.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davidr on 13/06/2017.
 */
@Data
public class JsonValueLocator {

    private static final Configuration JSON_PATH_CONFIG = new Configuration
            .ConfigurationBuilder()
            .build()
            .jsonProvider(new JacksonJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .addOptions(Option.ALWAYS_RETURN_LIST)
            ;



    public static JsonValueLocator of(String path){
        JsonValueLocator locatorPath = new JsonValueLocator();
        locatorPath.path = path;
        return locatorPath;
    }

    private String path;

    public List<String> matches(LinkedHashMap<String,String> entity){
        List<Object> matches = JsonPath.using(JSON_PATH_CONFIG).parse(entity).read(path);

        return matches
                .stream()
                .map(match -> match.toString())
                .collect(Collectors.toList());
    }

}
