package com.massfords.maven.i18njson;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author slazarus
 */
@Mojo(name="i18njson", defaultPhase= LifecyclePhase.COMPILE)
public class I18NJsonPlugin extends AbstractMojo {

    @Parameter
    private List<String> i18nJsonFiles;

    public void execute() throws MojoExecutionException, MojoFailureException {
        validateFiles(i18nJsonFiles);
    }

    protected static void validateFiles(List<String> i18nJsonFiles) throws MojoExecutionException, MojoFailureException {
        for (String s : i18nJsonFiles) {
            JsonValidator validator;
            try {
                validator = new JsonValidator(new File(s));
            } catch (FileNotFoundException e) {
                throw new MojoExecutionException("Unable to find json file: \"" + s + "\"");
            }
            try {
                validator.validate();
            } catch (I18NJsonValidationException e) {
                throw new MojoFailureException("Validation failed for file \"" + s + "\": " + e.getMessage());
            }
        }
    }

}
