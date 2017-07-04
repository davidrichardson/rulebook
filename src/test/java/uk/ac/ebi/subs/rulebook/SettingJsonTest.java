package uk.ac.ebi.subs.rulebook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import net.minidev.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Created by davidr on 04/07/2017.
 */
public class SettingJsonTest {


    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void makeADoc() throws JsonProcessingException {

        /**
         * JayWay JSONPath doesn't let you create new keys, so we need something in the document to work with
         */
        String template = objectMapper.writeValueAsString(new SpaceShip());

        DocumentContext documentContext = JsonPath.parse(template);

        documentContext.set("$.serialNumber", "BS-75");

        JSONArray attributes = new JSONArray();

        /**
         * If you don't call .json() on the DocumentContexts, JSON path just adds '{}'
         */
        attributes.add(makeAttribute("crew_capacity", "3000").json());
        attributes.add(makeAttribute("passenger_capacity", "0").json());

        documentContext.set("$.attributes", attributes);


        System.out.println(documentContext.jsonString());
    }

    private DocumentContext makeAttribute(String attributeName, String attributeValue) throws JsonProcessingException {
        String template = objectMapper.writeValueAsString(new Attribute());
        DocumentContext attributeDoc = JsonPath.parse(template);

        attributeDoc.set("$.name", attributeName);
        attributeDoc.set("$.value", attributeValue);


        return attributeDoc;
    }


    public class Attribute {
        private String name;
        private String value;
        private String units;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }

    public class SpaceShip {
        private String serialNumber;
        private List<Attribute> attributes = new ArrayList<>();

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public List<Attribute> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<Attribute> attributes) {
            this.attributes = attributes;
        }
    }
}
