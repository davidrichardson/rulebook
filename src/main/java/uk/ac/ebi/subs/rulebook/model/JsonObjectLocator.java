package uk.ac.ebi.subs.rulebook.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import lombok.Data;

import java.util.*;

import com.jayway.jsonpath.JsonPath;

/**
 * Created by Dave on 09/06/2017.
 */
@Data
public class JsonObjectLocator {

    private static final Configuration JSON_PATH_CONFIG = new Configuration
            .ConfigurationBuilder()
            .build()
            .jsonProvider(new JacksonJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .addOptions(Option.ALWAYS_RETURN_LIST)
            ;



    public static JsonObjectLocator of(String path){
        JsonObjectLocator locatorPath = new JsonObjectLocator();
        locatorPath.path = path;
        return locatorPath;
    }

    private String path;

    public List<LinkedHashMap<String,String>> matches(String document){

        return JsonPath.using(JSON_PATH_CONFIG).parse(document).read(path);
    }

}
