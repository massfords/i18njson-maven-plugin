package com.massfords.maven.i18njson;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slazarus
 */
public class I18NJsonPluginTest {

    private I18NJsonPlugin plugin = new I18NJsonPlugin();

    @Test
    public void testValidateFiles() throws Exception {
        String validJsonPath = getClass().getResource("testdata/valid.json").getFile();
        List<String> paths = new ArrayList<String>();
        paths.add(validJsonPath);
        plugin.validateFiles(paths);
    }

}
