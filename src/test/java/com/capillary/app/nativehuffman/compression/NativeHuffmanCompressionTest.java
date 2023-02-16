package com.capillary.app.nativehuffman.compression;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

import static org.junit.Assert.*;

public class NativeHuffmanCompressionTest {
    NativeHuffmanCompression cObj;
    @Before
    public void setUp() throws Exception {
        cObj = new NativeHuffmanCompression();
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void getHeader_NormalCase() throws IOException {
        Map<Character, Integer> mp = new HashMap<>();
        mp.put('a', 3);
        mp.put('b', 2);
        mp.put('c', 1);

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutputStream serial = new ObjectOutputStream(bStream);

        serial.writeObject(mp);
        serial.close();

        byte[] mapContent = bStream.toByteArray();
        byte[] mapSize=(mapContent.length+"\n").getBytes();
        bStream.close();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( mapSize );
        outputStream.write( mapContent );

        byte expected[] = outputStream.toByteArray( );
        outputStream.close();

        byte[] result= cObj.writeObjects(mp);
        assertArrayEquals(expected,result);
    }

    @Test
    public void getHeader_MapIsNull() throws IOException {
        Map<Character, Integer> mp = null;

        assertThrows(RuntimeException.class, () -> cObj.writeObjects(mp));
    }

    @Test
    public void getHeader_MapIsEmpty() throws IOException {
        Map<Character, Integer> mp = new HashMap<>();

        assertThrows(RuntimeException.class, () -> cObj.writeObjects(mp));
    }

    @Test
    public void getCompressedBytes() {
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
}