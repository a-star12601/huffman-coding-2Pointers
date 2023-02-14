package com.capillary.app.zipper;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;

public class HuffmanZipperUnzipperTest {
    private static HuffmanZipperUnzipper m;

    @Mock
    HuffmanZipperUnzipper mockZip;
    @Mock
    PassableObject mockObj;

    @Test
    public void checkFilesEmpty() {
        m=new HuffmanZipperUnzipper();
        assertFalse("File is Empty",m.CheckFileNotEmpty("Empty.txt"));
        assertTrue("File is Not Empty",m.CheckFileNotEmpty("Ascii.txt"));
    }

    @Test
    public void checkFileExists() {
        m=new HuffmanZipperUnzipper();
        assertTrue("File Exists",m.CheckFileExists("Empty.txt"));
        assertFalse("File Not Exists",m.CheckFileExists("Ascii2.txt"));
    }

    @Test
    public void testOverall(){
        m=new HuffmanZipperUnzipper();
        PassableObject ob=new PassableObject("pg100.txt","a.txt");
        m.encode(ob);
        m.decode(ob);
        assertTrue(true);
    }

    @Test
    public void ExceptionInDecodeCNF() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        mockObj.compressed="As.txt";
        doThrow(ClassNotFoundException.class).when(mockZip).decodeAbstract(any(),any());
        doCallRealMethod().when(mockZip).decode(mockObj);
        assertThrows(RuntimeException.class,()->mockZip.decode(mockObj));
    }
    @Test
    public void ExceptionInDecodeIO() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        mockObj.compressed="As.txt";
        doThrow(IOException.class).when(mockZip).decodeAbstract(any(),any());
        doCallRealMethod().when(mockZip).decode(mockObj);
        assertThrows(RuntimeException.class,()->mockZip.decode(mockObj));
    }

}