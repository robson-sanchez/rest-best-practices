package nl.mca.examples.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

public abstract class BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseResource.class);

    protected <T> T partialResponse(T content, List<String> fields) {
        if (fields.isEmpty()) {
            return content;
        }

        Class contentClass = content.getClass();

        try {
            T newInstance = (T) contentClass.newInstance();

            Stream.of(contentClass.getDeclaredFields())
                    .filter(field -> fields.contains(field.getName()))
                    .forEach(field -> {
                        field.setAccessible(true);

                        try {
                            Object value = field.get(content);
                            field.set(newInstance, value);
                        } catch (IllegalAccessException e) {
                            LOGGER.error("Fail to set field value" + field.getName());
                        }
                    });

            return newInstance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Fail to create partial response");
        }

    }
}
