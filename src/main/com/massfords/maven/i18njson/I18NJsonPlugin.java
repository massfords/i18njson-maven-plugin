package com.massfords.maven.i18njson;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author slazarus
 */
@Mojo(name="i18njson", defaultPhase= LifecyclePhase.COMPILE)
public class I18NJsonPlugin extends AbstractMojo {

    @Component
    private MavenProject project;

    @Parameter
    private List<String> i18nJsonFiles;

    public void execute() throws MojoExecutionException, MojoFailureException {
        List<File> jsonFiles = new ArrayList<File>();
        for (String s : i18nJsonFiles) {
            jsonFiles.add(new File(project.getBasedir(), s));
        }
        validateFiles(jsonFiles);
    }

    protected static void validateFiles(List<File> i18nJsonFiles) throws MojoExecutionException, MojoFailureException {
        for (File f : i18nJsonFiles) {
            JsonValidator validator;
            try {
                validator = new JsonValidator(f);
            } catch (FileNotFoundException e) {
                throw new MojoExecutionException("Unable to find json file: \"" + f.getPath() + "\"");
            }
            try {
                validator.validate();
            } catch (I18NJsonValidationException e) {
                throw new MojoFailureException("Validation failed for file \"" + f.getPath() + "\": " + e.getMessage());
            }
        }
    }

}
