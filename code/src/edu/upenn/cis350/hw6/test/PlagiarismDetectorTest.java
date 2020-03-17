package edu.upenn.cis350.hw6.test;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import edu.upenn.cis350.hw6.PlagiarismDetector;

public class PlagiarismDetectorTest {

    @Test
    public void test() {
        String directory = "../../data";
        Map<String, Integer> map = null;

        // run the method
        try {
            long start = System.currentTimeMillis();
            map = PlagiarismDetector.detectPlagiarism(directory, 4, 5);
            long end = System.currentTimeMillis();
            System.out.println("Took " + (end - start) + "ms to run");
        } catch (Exception e) { // oops, got an exception
            fail(
                "INCORRECT OUTPUT: detectPlagiarism throws "
                + e.toString() + " when windowSize = 4 and threshold = 5"
            );
        }
        
        // make sure the method didn't return null
        if (map == null) {
            fail(
                "INCORRECT OUTPUT: Map returned by detectPlagiarism is null "
                + "when windowSize = 4 and threshold = 5"
            );
        }
        
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        System.out.println("Here are the values in the Map:");
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // check that output has right number of elements
        if (entries.size() != 10) {
            fail(
                "INCORRECT OUTPUT: Map returned by detectPlagiarism has incorrect number of " + 
                "entries; should return Map with 10 elements when windowSize = 4 and threshold = 5"
            );
        }
        
        // check that the collection has the right elements in the right order
        int count = 0;
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            if (key == null) {
                fail(
                    "INCORRECT OUTPUT: Key in Map returned by detectPlagiarism is null when " + 
                    "windowSize = 4 and threshold = 5"
                );
            }

            int value = entry.getValue();

            if (count == 0) { // bwa242.txt-sra42.txt: 10
                if (!key.contains("bwa242.txt") || !key.contains("sra42.txt") || value != 10) {
                    fail(
                        "INCORRECT OUTPUT: incorrect key/value pair in Map; first match should " + 
                        "be bwa242.txt-sra42.txt with value 10 when windowSize = 4 and theshold = 5"
                    );
                }
            } else if (count == 1 || count == 2) {
                if (value != 8) {
                    fail(
                        "INCORRECT OUTPUT: incorrect value in Map; second and third matches " + 
                        "should have value 8 when windowSize = 4 and theshold = 5"
                    );
                } else if (!((key.contains("bwa242.txt") && key.contains("bwa249.txt"))
                        || (key.contains("bwa242.txt") && key.contains("bwa0.txt")))) {
                    fail(
                        "INCORRECT OUTPUT: incorrect key/value pair in Map; second and third " + 
                        "matches should be bwa242.txt-bwa249.txt and bwa0.txt-bwa242.txt " + 
                        "(in some order) with value 8 when windowSize = 4 and theshold = 5"
                    );
                }   
            } else if (count == 3 || count == 4 || count == 5) {
                if (value != 7) {
                    fail(
                        "INCORRECT OUTPUT: incorrect value in Map; fourth, fifth, and sixth " + 
                        "matches should have value 7 when windowSize = 4 and theshold = 5"
                    );
                } else if (!((key.contains("edo20.txt") && key.contains("edo26.txt"))
                        || (key.contains("bwa132.txt") && key.contains("bwa137.txt"))
                        || (key.contains("bwa132.txt") && key.contains("bwa133.txt")))) {
                    fail(
                        "INCORRECT OUTPUT: incorrect key/value pair in Map; key [" + key + 
                        "] with value 7 is incorrect when windowSize = 4 and theshold = 5"
                    );
                }
            } else if (count >= 6) { 
                if (value != 6) {
                    fail("INCORRECT OUTPUT: incorrect value in Map; seventh through tenth " + 
                        "matches should have value 6 when windowSize = 4 and theshold = 5"
                    );
                } else if (!((key.contains("bwa233.txt") && key.contains("bwa242.txt"))
                        || (key.contains("bwa242.txt") && key.contains("ecu201.txt"))
                        || (key.contains("sra126.txt") && key.contains("sra42.txt"))
                        || (key.contains("bwa0.txt") && key.contains("bwa132.txt")))) {
                    fail(
                        "INCORRECT OUTPUT: incorrect key/value pair in Map; key [" + key +
                        "] with value 6 is incorrect when windowSize = 4 and theshold = 5"
                    );
                }
            }
            count++;
        }
    }

}
