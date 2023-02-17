package com.capillary.app.general;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class ValidatorTest {
    private static Validator v;
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    @Before
    public void setUp() throws Exception {
        v = new Validator();

    }

    @Test
    public void validate() throws IOException {
        File file1= tempFolder.newFile("ValidFile.txt");
        BufferedWriter bw=new BufferedWriter(new FileWriter(file1));
        bw.write("dummmy file content");
        bw.close();

        File file2= tempFolder.newFile("Empty.txt");


        assertTrue(v.validate("Ascii.txt", "b.txt", "c.txt"));
        assertFalse(v.validate("Ascii.txt", "b", "c.txt"));
        assertFalse(v.validate("Ascii.txt", "b.txt", "c"));
        assertFalse(v.validate("a", "b.txt", "c.txt"));
        assertFalse(v.validate("a.txt", "b.txt", "c.txt"));
        assertFalse(v.validate("Empty.txt", "b.txt", "c.txt"));
    }
}