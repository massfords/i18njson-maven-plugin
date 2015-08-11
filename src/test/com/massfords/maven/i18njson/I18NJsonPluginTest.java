package com.massfords.maven.i18njson;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author slazarus
 */
public class I18NJsonPluginTest {

    private I18NJsonPlugin plugin = new I18NJsonPlugin();

    @Test
    public void testValidateFiles() throws Exception {
        File validJsonFile = new File(getClass().getResource("testdata/valid.json").getFile());
        List<File> files = new ArrayList<File>();
        files.add(validJsonFile);
        plugin.validateFiles(files);
    }

}
