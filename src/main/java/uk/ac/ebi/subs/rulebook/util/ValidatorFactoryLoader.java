package uk.ac.ebi.subs.rulebook.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import uk.ac.ebi.subs.rulebook.validation.ValidatorFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dave on 09/06/2017.
 */
public class ValidatorFactoryLoader {

    public Map<String, ValidatorFactory> getValidatorFactories() {

        Reflections reflections = new Reflections();
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, ValidatorFactory> validatorFactoryMap = new HashMap<>();

        for (Class<? extends ValidatorFactory> factoryClass : reflections.getSubTypesOf(ValidatorFactory.class)) {
            ValidatorFactory factory;
            try {
                Constructor<? extends ValidatorFactory> factoryConstructor = factoryClass.getConstructor();
                factory = factoryConstructor.newInstance();
                factory.setObjectMapper(objectMapper);

                if (validatorFactoryMap.containsKey(factory.getValidatorTypeName())) {
                    throw new IllegalStateException("Multiple validator factories with the same name found on the classpath: " +
                            factoryClass.getName() + " and " + validatorFactoryMap.get(factory.getValidatorTypeName()).getClass().getName() +
                            " both use the type name " + factory.getValidatorTypeName());
                }

                validatorFactoryMap.put(factory.getValidatorTypeName(), factory);

            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }


        }

        return validatorFactoryMap;
    }

}
