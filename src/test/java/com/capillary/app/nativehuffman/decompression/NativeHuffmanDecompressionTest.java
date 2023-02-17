package com.capillary.app.nativehuffman.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.zipper.decompression.IDecompression;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class NativeHuffmanDecompressionTest {
    IDecompression dObj;

    @Before
    public void setUp() throws Exception {
        dObj = new NativeHuffmanDecompression();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetCharCount_NormalCase() {
        Map<Character, Integer> mp = new HashMap<>();
        mp.put('a', 3);
        mp.put('b', 2);
        mp.put('c', 1);

        int expected = 6;
        int result = dObj.getCharCount(mp);

        assertEquals(expected, result);
    }

    @Test
    public void testGetDecompressedBytes_NormalCase() {
        byte[] temp = new byte[]{115, 0};

        Node child1=new Node("a",3);
        Node child2=new Node("b",2);
        Node child3=new Node("c",1);
        Node int1=new Node(child3,child2);
        Node tree = new Node(child1,int1);

        int cnt = 6;

        byte[] expected = "abcaba".getBytes();

        byte[] result = dObj.getDecompressedBytes(temp, tree, cnt);

        assertArrayEquals(expected, result);
    }
}