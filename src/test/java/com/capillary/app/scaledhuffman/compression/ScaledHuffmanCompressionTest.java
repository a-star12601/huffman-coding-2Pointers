package com.capillary.app.scaledhuffman.compression;

import com.capillary.app.zipper.compression.ICompression;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ScaledHuffmanCompressionTest {
    ICompression cObj;
    @Before
    public void setUp() throws Exception {
        cObj = new ScaledHuffmanCompression();
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void testGetCompressedBytes_NormalCase() {
        byte[] inputByteArray = "ab ab a ab".getBytes();

        Map<String, String> inputTable = new HashMap<>();
        inputTable.put("ab", "0");
        inputTable.put(" ", "11");
        inputTable.put("a", "10");

        Byte[] temp = new Byte[]{110, -64};
        List<Byte> expected = Arrays.asList(temp);

        List<Byte> result = cObj.getCompressedBytes(inputByteArray, inputTable);

        assertEquals(expected,result);
    }

    @Test
    public void testGetCompressedBytes_ExtendedCase() {
        byte[] inputByteArray = "a".getBytes();

        Map<String, String> inputTable = new HashMap<>();
        inputTable.put("a", "1000000000");
        Byte[] temp = new Byte[]{-128, 0};
        List<Byte> expected = Arrays.asList(temp);

        List<Byte> result = cObj.getCompressedBytes(inputByteArray, inputTable);

        assertEquals(expected,result);
    }

    @Test
    public void testGetCompressedBytes_ArrayNullCase(){
        assertThrows(RuntimeException.class, ()->cObj.getCompressedBytes(null,new HashMap<>()));
    }

    @Test
    public void testGetCompressedBytes_ArrayEmptyCase(){
        assertThrows(RuntimeException.class, ()->cObj.getCompressedBytes(new byte[0],new HashMap<>()));
    }
    @Test
    public void testGetCompressedBytes_HashMapNullCase(){
        assertThrows(RuntimeException.class, ()->cObj.getCompressedBytes("abc".getBytes(),null));
    }
}