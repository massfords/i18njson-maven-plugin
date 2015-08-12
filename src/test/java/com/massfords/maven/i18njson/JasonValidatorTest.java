package com.massfords.maven.i18njson;

import org.junit.Test;

import java.io.InputStream;

/**
 * @author slazarus
 */
public class JasonValidatorTest {

    @Test
    public void testValidFile() throws Exception {
        doValidate("/testdata/valid.json");
    }

    @Test(expected=I18NJsonValidationException.class)
    public void testDuplicateKeys() throws Exception {
        doValidate("/testdata/invalid-duplicate-keys.json");
    }

    @Test(expected=I18NJsonValidationException.class)
    public void testNestedData() throws Exception {
        doValidate("/testdata/invalid-nested-data.json");
    }

    private void doValidate(String path) throws Exception {
        InputStream is = getClass().getResourceAsStream(path);
        JsonValidator.validate(is);
    }
}
