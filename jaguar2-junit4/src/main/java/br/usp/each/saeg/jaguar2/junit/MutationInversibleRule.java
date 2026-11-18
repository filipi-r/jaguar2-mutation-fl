package br.usp.each.saeg.jaguar2.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class MutationInversibleRule implements TestRule {

    public static final String PROPERTY_ACTIVE = "jaguar2.mbfl.active";
    public static final String PROPERTY_FAILING_TESTS_FILE = "jaguar2.mbfl.failingTestsFile";

    @Override
    public Statement apply(Statement base, Description description) {
        if(!Boolean.getBoolean(PROPERTY_ACTIVE)) return base;

        String testId = JaguarTestRunner.getTestId(description);

        return new Statement() {
            @Override 
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch(Throwable t) {
                    ResultCollector.recordFailingTest(testId);
                }
            }
        };
    }
}
