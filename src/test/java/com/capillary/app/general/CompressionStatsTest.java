package com.capillary.app.general;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class CompressionStatsTest {
    CompressionStats compStats;

    @Before
    public void setUp() throws Exception {
        compStats = new CompressionStats();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCompareFiles_NormalCase() throws IOException {
        File file1 = tempFolder.newFile("testFile1.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
        bw.write("abcaba");
        bw.close();

        File file2 = tempFolder.newFile("testFile2.txt");
        bw = new BufferedWriter(new FileWriter(file2));
        bw.write("abcdef");
        bw.close();

        assertTrue(compStats.compareFiles(file1.getPath(), file1.getPath()));
        assertFalse(compStats.compareFiles(file1.getPath(), file2.getPath()));
    }

    @Test
    public void testCompareFiles_NormalCase_FilNotFound() throws IOException {
        assertThrows(RuntimeException.class, () -> compStats.compareFiles("FileNotFound.txt", "FileNotFound.txt"));
        assertThrows(RuntimeException.class, () -> compStats.compareFiles("FileNotFound.txt", "FileNotFound.txt"));
    }
}