package com.capillary.app.general;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class FileOperationsTest {
    private static FileOperations f;
    @Mock
    FileOperations fMock;
    @Test
    public void readFile() throws IOException {
            f = new FileOperations();
            byte[] arr = f.readFile("TestAB.txt");
            Assert.assertTrue(arr.length > 0);
            Assert.assertArrayEquals(arr, "abaaba".getBytes());
    }

    @Test
    public void compareFiles() {
        f=new FileOperations();
        Assert.assertTrue(f.compareFiles("MultiA.txt","MultiACopy.txt"));
        Assert.assertFalse(f.compareFiles("MultiA.txt","As.txt"));
    }
    @Test
    public void compareFilesMock() throws IOException {
        MockitoAnnotations.initMocks(this);
        Mockito.doThrow(IOException.class).when(fMock).readFile("dummy");
        Mockito.doCallRealMethod().when(fMock).compareFiles("dummy","dummy");
        assertThrows(RuntimeException.class,()->fMock.compareFiles("dummy","dummy"));
    }

    @Test
    public void TestByteList(){
        f=new FileOperations();
        byte[] arr={100,20};
        List<Byte> list=new ArrayList<>();
        list.add((byte) 100);
        list.add((byte) 20);
        assertArrayEquals(arr,f.byteFromByteList(list));
    }

    @Test
    public void TestWriting() throws IOException {
        f=new FileOperations();
        byte[] arr={100,20};
        f.writeToFile("writeTest",false,arr);
        File f1=new File("writeTest");
        assertEquals(f1.length(),arr.length);
        f.writeToFile("writeTest",true,arr);
        assertEquals(f1.length(),2*arr.length);
        f1.delete();
    }
}