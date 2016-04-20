package com.lucwo.railroad;

import nl.bigo.rrdantlr4.DiagramGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.codehaus.plexus.compiler.util.scan.InclusionScanException;
import org.codehaus.plexus.compiler.util.scan.SimpleSourceInclusionScanner;
import org.codehaus.plexus.compiler.util.scan.SourceInclusionScanner;
import org.codehaus.plexus.compiler.util.scan.mapping.SourceMapping;
import org.codehaus.plexus.compiler.util.scan.mapping.SuffixMapping;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Mojo(name = "railroad", defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        requiresProject = true)
public class RailroadGenerator extends AbstractMojo {

    @Parameter
    protected Set<String> includes = new HashSet<String>();
    /**
     * A set of Ant-like exclusion patterns used to prevent certain files from
     * being processed. By default, this set is empty such that no files are
     * excluded.
     */
    @Parameter
    protected Set<String> excludes = new HashSet<String>();

    /**
     * The directory where the ANTLR grammar files ({@code *.g4}) are located.
     */
    @Parameter(defaultValue = "${basedir}/src/main/antlr4")
    private File sourceDirectory;

    /**
     * Specify output directory where the Java files are generated.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/railroad")
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            processGrammarFiles();
        } catch (InclusionScanException e) {
            throw new MojoExecutionException("Fatal error occured while evaluating the names of the grammar files to analyze", e);
        }


    }

    public void processGrammarFiles() throws InclusionScanException, MojoExecutionException {

        SourceMapping mapping = new SuffixMapping("g4", Collections.<String>emptySet());

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        Set<String> includes = getIncludesPatterns();

        SourceInclusionScanner scan = new SimpleSourceInclusionScanner(includes, excludes);

        scan.addSourceMapping(mapping);
        Set<File> grammarFiles = scan.getIncludedSources(sourceDirectory, null);

        for (File grammarFile : grammarFiles) {
            try {
                DiagramGenerator generator = new DiagramGenerator(grammarFile.getAbsolutePath());
                for(String ruleName : generator.getRules().keySet()) {
                    String svg = generator.getSVG(ruleName);
                }
                String path = grammarFile.getName() + ".html";
                boolean success = generator.createHtml(path);
            } catch (IOException e) {
                throw new MojoExecutionException("Could not read grammar file", e);
            }
        }

    }

    public Set<String> getIncludesPatterns() {
        if (includes == null || includes.isEmpty()) {
            return Collections.singleton("**/*.g4");
        }
        return includes;
    }
}