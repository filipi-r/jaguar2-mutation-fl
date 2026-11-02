package br.usp.each.saeg.jaguar2.junit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class MutationInversibleRule implements TestRule {

    public static final String PROPERTY_ACTIVE = "jaguar2.mbfl.active";
    public static final String PROPERTY_FAILING_TESTS_FILE = "jaguar2.mbfl.failingTestsFile";

    private final Set<String> failingTests;

    public MutationInversibleRule() {
        if(Boolean.getBoolean(PROPERTY_ACTIVE))
            this.failingTests = loadFailingTests();
        else 
            this.failingTests = Collections.emptySet();
    }

    private Set<String> loadFailingTests() {
        String filePath = System.getProperty(PROPERTY_FAILING_TESTS_FILE);
        if(filePath == null)
            filePath = "target/failing-tests.txt";
        
        try {
            File failingTestsFile = new File(filePath); 
            HashSet<String> set = new HashSet<>(Files.readAllLines(failingTestsFile.toPath()));
            return set;
        } catch (IOException e) {
            return Collections.emptySet();
        }
    }

    @Override
    public Statement apply(Statement base, Description description) {
        if(!Boolean.getBoolean(PROPERTY_ACTIVE)) return base;

        String testId = JaguarTestRunner.getTestId(description);
        boolean isOriginallyFailing = failingTests.contains(testId);

        return new Statement() {
            @Override 
            public void evaluate() throws Throwable {
                boolean isMutantFixed = false;
                if(!isOriginallyFailing) base.evaluate();
                else {
                    try {
                        base.evaluate();
                        isMutantFixed = true;
                    } catch(Throwable t) {
                        
                    }
                }

                if(isMutantFixed) {
                    throw new AssertionError();
                }
            }
        };
    }
}
