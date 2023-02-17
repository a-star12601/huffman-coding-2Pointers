package com.capillary.app.nativehuffman.compression;

import com.capillary.app.zipper.compression.ICompression;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class NativeHuffmanCompressionTest {
    ICompression cObj;
    @Before
    public void setUp() throws Exception {
        cObj = new NativeHuffmanCompression();
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void testGetCompressedBytes_NormalCase() {
        byte[] inputByteArray = "abcaba".getBytes();

        Map<Character, String> inputTable = new HashMap<>();
        inputTable.put('a', "0");
        inputTable.put('b', "11");
        inputTable.put('c', "10");

        Byte[] temp = new Byte[]{115, 0};
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

    @Test
    public void testByteFromByteList(){
        List<Byte> input=Arrays.asList(new Byte[]{-115,0});
        byte[] expected=new byte[]{-115,0};
        byte[] result= cObj.byteFromByteList(input);
        assertArrayEquals(expected,result);

    }
}