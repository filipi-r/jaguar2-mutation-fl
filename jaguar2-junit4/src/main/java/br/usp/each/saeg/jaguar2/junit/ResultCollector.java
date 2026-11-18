package br.usp.each.saeg.jaguar2.junit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ResultCollector {
    public static void clear() {
        try{
            String filePath = System.getProperty(MutationInversibleRule.PROPERTY_FAILING_TESTS_FILE);
            if (filePath == null)
                filePath = "target/failing-tests.txt";

            File file = new File(filePath);
            if(file.getParentFile() != null)
                file.getParentFile().mkdirs();
            new BufferedWriter(new FileWriter(file, false)).close();
        } catch(IOException e) {
            System.err.println("Cannot use file: " + e.getMessage());
        }
    }

    public static void recordFailingTest(String test) {
        String filePath = System.getProperty(MutationInversibleRule.PROPERTY_FAILING_TESTS_FILE);
        if(filePath == null)
            filePath = "target/failing-tests.txt";

        try {
            File file = new File(filePath);
            if(file.getParentFile() != null)
                file.getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(test);
            writer.newLine();

            writer.close();
        } catch (IOException e){
            System.err.println("Failed to write failing tests file: " + e.getMessage());
        }
    }

    public static Set<String> getFailingTests() {
        String filePath = System.getProperty(MutationInversibleRule.PROPERTY_FAILING_TESTS_FILE);
        if (filePath == null)
            filePath = "target/failing-tests.txt";

        Set<String> failingTests = new HashSet<String>();
        File file = new File(filePath);
        if (!file.exists())
            return failingTests;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String test;
            while ((test = reader.readLine()) != null)
                failingTests.add(test);
        } catch (IOException e) {
            System.err.println("Failed to read failing tests file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Failed to close failing tests file: " + e.getMessage());
                }
            }
        }
        return failingTests;
    }
}
