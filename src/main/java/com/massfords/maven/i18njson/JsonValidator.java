package com.massfords.maven.i18njson;


import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Valid files should contain no nested values (lists / objects within a key value pair of the root object),
 * or duplicate keys.
 *
 * Example:
 *
 * {
 *   "a": "hello",
 *   "b": "world"
 * }
 *
 * @author slazarus
 */
public class JsonValidator {

    public static void validate(InputStream inputStream) throws I18NJsonValidationException, IOException {

        try (JsonParser parser = Json.createParser(inputStream)) {

            Set<String> keys = new HashSet<>();
            Set<String> duplicates = new HashSet<>();

            if (!parser.hasNext()) {
                // this is fatal, no point continuing looking
                throw new I18NJsonValidationException("File requested for JSON validation was empty");
            }
            JsonParser.Event event = parser.next();
            if (event != JsonParser.Event.START_OBJECT) {
                // this is fatal, no point continuing looking
                throw new I18NJsonValidationException("JSON contained no configuration object");
            }
            boolean foundEndObject = false;
            while (parser.hasNext()) {
                event = parser.next();
                if (event == JsonParser.Event.END_OBJECT) {
                    foundEndObject = true;
                    break;
                }
                if (event != JsonParser.Event.KEY_NAME) {
                    // this is fatal, no point continuing looking
                    throw new I18NJsonValidationException("Missing or invalid key in properties object");
                }
                final String key = parser.getString();
                if (!keys.add(key)) {
                    duplicates.add(key);
                }
                event = parser.next();
                if (event != JsonParser.Event.VALUE_STRING) {
                    // this is fatal, no point continuing looking
                    throw new I18NJsonValidationException("Found non-string value for key \"" + key + "\"");
                }
            }
            if (!foundEndObject) {
                // this is fatal, no point continuing looking
                throw new I18NJsonValidationException("Failed to find end object token!");
            }
            if (duplicates.size() > 0) {
                throw new I18NJsonValidationException("Found duplicate keys: " + duplicates);
            }
        }
    }

    public static void validate(File f) throws IOException, I18NJsonValidationException {
        validate(new FileInputStream(f));
    }
}
