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
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author slazarus
 */
@Mojo(name="i18njson", defaultPhase= LifecyclePhase.COMPILE)
public class I18NJsonPlugin extends AbstractMojo {

    @Component
    private MavenProject project;

    @Parameter
    private List<String> i18nJsonFiles = Collections.emptyList();

    public void execute() throws MojoExecutionException, MojoFailureException {
        i18nJsonFiles.stream().forEach(s->
                getLog().info("i18njson: checking: " + s)
        );
        JsonValidationReport report = validatePaths(i18nJsonFiles);
        File reportFile = new File(this.project.getModel().getBuild().getDirectory(),
                "i18njson-maven-plugin/report.json");
        report.createReportFile(reportFile);
        if (report.getInvalid() > 0) {
            report.getErrors().stream().forEach(s->getLog().error(s));
            throw new MojoFailureException("i18njson: Failures. See report file " +
                    "in target/i18njson-maven-plugin/report.json for details.");
        }
    }

    protected JsonValidationReport validatePaths(List<String> paths) {
        return validateFiles(
                paths
                .stream()
                .map(s -> new File(project.getBasedir(), s))
                .collect(Collectors.toList())
        );
    }

    protected JsonValidationReport validateFiles(List<File> i18nJsonFiles) {
        JsonValidationReport report = new JsonValidationReport();
        for (File f : i18nJsonFiles) {
            report.addFile(f.getAbsolutePath());
            try {
                JsonValidator.validate(f);
            } catch (FileNotFoundException e) {
                report.failure("i18njson: unable to find json file: \"" + f.getPath() + "\"");
                continue;
            } catch (I18NJsonValidationException e) {
                report.failure("i18njson: failed for file: \"" + f.getPath() + "\": " + e.getMessage());
                continue;
            } catch (IOException e) {
                report.failure("i18njson: unable to read json file: \"" + f.getPath() + "\"");
                continue;
            }
            report.success();
        }
        return report;
    }

}
