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
    private static NativeHuffmanZipperUnzipper m;

    @Mock
    NativeHuffmanZipperUnzipper mockZip;
    @Mock
    PassableObject mockObj;

    @Test
    public void checkFilesEmpty() {
        m=new NativeHuffmanZipperUnzipper();
        assertFalse("File is Empty",m.CheckFileNotEmpty("Empty.txt"));
        assertTrue("File is Not Empty",m.CheckFileNotEmpty("Ascii.txt"));
    }

    @Test
    public void checkFileExists() {
        m=new NativeHuffmanZipperUnzipper();
        assertTrue("File Exists",m.CheckFileExists("Empty.txt"));
        assertFalse("File Not Exists",m.CheckFileExists("Ascii2.txt"));
    }

    @Test
    public void testOverall(){
        m=new NativeHuffmanZipperUnzipper();
        PassableObject ob=new PassableObject("pg100.txt","a.txt");
        m.compress(ob);
        m.decompress(ob);
        assertTrue(true);
    }

    @Test
    public void ExceptionInDecodeCNF() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        mockObj.compressed="As.txt";
        doThrow(ClassNotFoundException.class).when(mockZip).decodeAbstract(any(),any());
        doCallRealMethod().when(mockZip).decompress(mockObj);
        assertThrows(RuntimeException.class,()->mockZip.decompress(mockObj));
    }
    @Test
    public void ExceptionInDecodeIO() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        mockObj.compressed="As.txt";
        doThrow(IOException.class).when(mockZip).decodeAbstract(any(),any());
        doCallRealMethod().when(mockZip).decompress(mockObj);
        assertThrows(RuntimeException.class,()->mockZip.decompress(mockObj));
    }

}