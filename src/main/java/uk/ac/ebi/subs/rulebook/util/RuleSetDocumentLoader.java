package uk.ac.ebi.subs.rulebook.util;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import uk.ac.ebi.subs.rulebook.model.documents.RuleSetDocument;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
/**
 * Created by Dave on 09/06/2017.
 */
public class RuleSetDocumentLoader {

    public RuleSetDocument load(File jsonFile) {
        ObjectMapper mapper = new ObjectMapper();


        mapper.registerModule(new KeepAsJsonModule());

        try {
            return mapper.readValue(jsonFile, RuleSetDocument.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * from https://stackoverflow.com/questions/4783421/how-can-i-include-raw-json-in-an-object-using-jackson
     */
    public class KeepAsJsonDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {

            TreeNode tree = jp.getCodec().readTree(jp);
            return tree.toString();
        }
    }


    public class KeepAsJsonModule extends Module {

        @Override
        public void setupModule(SetupContext context) {
            context.addBeanDeserializerModifier(new BeanDeserializerModifierImpl() {
            });
        }

        @Override
        public String getModuleName() {
            return "KeepAsJson";
        }

        @Override
        public Version version() {
            return Version.unknownVersion();
        }
    }

    public class BeanDeserializerModifierImpl extends BeanDeserializerModifier {
        @Override
        public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc, BeanDeserializerBuilder builder) {
            Iterator<SettableBeanProperty> it = builder.getProperties();
            while (it.hasNext()) {
                SettableBeanProperty p = it.next();
                if (p.getAnnotation(JsonRawValue.class) != null) {
                    builder.addOrReplaceProperty(p.withValueDeserializer(new KeepAsJsonDeserializer()), true);
                }
            }
            return builder;
        }
    }
}
