package com.massfords.maven.i18njson;

import org.junit.Test;

import java.io.InputStream;

/**
 * @author slazarus
 */
public class JsonValidatorTest {

    @Test
    public void testValidFile() throws Exception {
        InputStream is = getClass().getResourceAsStream("testdata/valid.json");
        JsonValidator validator = new JsonValidator(is);
        validator.validate();
    }

    @Test(expected=I18NJsonValidationException.class)
    public void testDuplicateKeys() throws Exception {
        InputStream is = getClass().getResourceAsStream("testdata/invalid-duplicate-keys.json");
        JsonValidator validator = new JsonValidator(is);
        validator.validate();
    }

    @Test(expected=I18NJsonValidationException.class)
    public void testNestedData() throws Exception {
        InputStream is = getClass().getResourceAsStream("testdata/invalid-nested-data.json");
        JsonValidator validator = new JsonValidator(is);
        validator.validate();
    }

}
