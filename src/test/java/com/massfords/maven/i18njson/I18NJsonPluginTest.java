package com.massfords.maven.i18njson;

import org.junit.Assert;
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
        List<File> files = new ArrayList<>();
        files.add(new File(getClass().getResource("/testdata/valid.json").getFile()));
        files.add(new File(getClass().getResource("/testdata/invalid-duplicate-keys.json").getFile()));
        files.add(new File(getClass().getResource("/testdata/invalid-nested-data.json").getFile()));
        JsonValidationReport report = plugin.validateFiles(files);
        Assert.assertEquals(report.getValid(), 1);
        Assert.assertEquals(report.getInvalid(), 2);
        Assert.assertEquals(report.getTotal(), 3);
    }

}
