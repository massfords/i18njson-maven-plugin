package com.massfords.maven.i18njson;


import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author slazarus
 */
public class JsonValidator {

    private JsonParser parser = null;

    private List<String> keys = new ArrayList<String>();

    public JsonValidator(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    public JsonValidator(InputStream in) {
        parser = Json.createParser(in);
    }

    public void validate() throws I18NJsonValidationException {
        if (!parser.hasNext()) {
            throw new I18NJsonValidationException("File requested for JSON validation was empty");
        }
        JsonParser.Event event = parser.next();
        if (event != JsonParser.Event.START_OBJECT) {
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
                throw new I18NJsonValidationException("Missing or invalid key in properties object");
            }
            final String key = parser.getString();
            if (keys.contains(key)) {
                throw new I18NJsonValidationException("Duplicate key \"" + key + "\" found.");
            }
            keys.add(key);
            event = parser.next();
            if (event != JsonParser.Event.VALUE_STRING) {
                throw new I18NJsonValidationException("Found non-string value for key \"" + key + "\"");
            }
        }
        if (!foundEndObject) {
            throw new I18NJsonValidationException("Failed to find end object token!");
        }
    }

}
