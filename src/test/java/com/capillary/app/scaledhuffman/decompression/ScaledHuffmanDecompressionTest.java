package com.capillary.app.scaledhuffman.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.zipper.decompression.IDecompression;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ScaledHuffmanDecompressionTest {

    IDecompression dObj;

    @Before
    public void setUp() throws Exception {
        dObj = new ScaledHuffmanDecompression();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetCharCount_NormalCase() {
        Map<String, Integer> mp = new HashMap<>();
        mp.put("ab", 3);
        mp.put("b", 2);
        mp.put("c", 1);

        int expected = 9;
        int result = dObj.getCharCount(mp);

        assertEquals(expected, result);
    }

    @Test
    public void testGetDecompressedBytes_NormalCase() {
        byte[] temp = new byte[]{110, -64};

        Node child1=new Node("ab",3);
        Node child2=new Node(" ",3);
        Node child3=new Node("a",1);
        Node int1=new Node(child3,child2);
        Node tree = new Node(child1,int1);

        int cnt = 10;

        byte[] expected = "ab ab a ab".getBytes();

        byte[] result = dObj.getDecompressedBytes(temp, tree, cnt);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testGetDecompressedBytes_ExtendedCase() {
        byte[] temp = "X".getBytes();
        Node child1=new Node("￢",1);
        Node child2=new Node("ﾙ",1);
        Node child3=new Node("ﾀ",1);
        Node int1=new Node(child3,child2);
        Node tree = new Node(child1,int1);

        int cnt = 3;

        byte[] expected = "’".getBytes();

        byte[] result = dObj.getDecompressedBytes(temp, tree, cnt);

        assertArrayEquals(expected, result);
    }
}