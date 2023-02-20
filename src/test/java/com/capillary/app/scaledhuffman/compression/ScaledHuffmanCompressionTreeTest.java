package com.capillary.app.scaledhuffman.compression;

import com.capillary.app.general.Node;
import com.capillary.app.zipper.compression.ICompressionTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ScaledHuffmanCompressionTreeTest {
    ICompressionTree cTree;

    @Before
    public void setUp() throws Exception {
        cTree = new ScaledHuffmanCompressionTree();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetFrequencyMap_NormalCase() {
        byte[] arr = "ab ab a".getBytes();

        Map<String, Integer> expected = new HashMap<>();
        expected.put("ab", 2);
        expected.put(" ", 2);
        expected.put("a", 1);

        Map<String, Integer> result = cTree.getFrequencyMap(arr);
        assertEquals(expected, result);
    }

    @Test
    public void testGetFrequencyMap_ArrayIsNull() {
        byte[] arr = null;

        assertThrows(RuntimeException.class, () -> cTree.getFrequencyMap(arr));
    }

    @Test
    public void testGetFrequencyMap_ArrayIsEmpty() {
        byte[] arr = "".getBytes();

        assertThrows(RuntimeException.class, () -> cTree.getFrequencyMap(arr));
    }

    public static boolean areIdentical(Node root1, Node root2) {

        if (root1 == null && root2 == null) {
            return true;
        }

        if (root1 != null && root2 != null) {
            return ((root1.value.equals(root2.value)) &&
                    areIdentical(root1.leftNode, root2.leftNode) &&
                    areIdentical(root1.rightNode, root2.rightNode));
        }

        return false;
    }

    @Test
    public void testGenerateTree_NormalCase() {
        Map<String, Integer> mp = new HashMap<>();
        mp.put("ab", 2);
        mp.put(" ", 2);
        mp.put("a", 1);

        Node child1=new Node("ab",2);
        Node child2=new Node(" ",2);
        Node child3=new Node("a",1);
        Node int1=new Node(child3,child2);
        Node expected=new Node(child1,int1);

        Node result = cTree.generateTree(mp);

        assertTrue(areIdentical(expected, result));
    }

    @Test
    public void testGenerateTree_SingleNodeCase() {
        Map<String, Integer> mp = new HashMap<>();
        mp.put("ab", 3);

        Node child1=new Node("ab",3);
        Node child2=new Node();
        Node expected=new Node(child1,child2);

        Node result = cTree.generateTree(mp);

        assertTrue(areIdentical(expected, result));
    }

    @Test
    public void testGenerateTree_MapIsNull() {
        Map<Character, Integer> mp = null;

        assertThrows(RuntimeException.class, () -> cTree.generateTree(mp));
    }

    @Test
    public void testGenerateTree_MapIsEmpty() {
        Map<Character, Integer> mp = new HashMap<>();

        assertThrows(RuntimeException.class, () -> cTree.generateTree(mp));

    }

    @Test
    public void testGetHashTable_NormalCase() {
        Node child1=new Node("ab",2);
        Node child2=new Node(" ",2);
        Node child3=new Node("a",1);
        Node int1=new Node(child3,child2);
        Node tree=new Node(child1,int1);

        Map<String, String> expected = new HashMap<>();
        expected.put("ab", "0");
        expected.put(" ", "11");
        expected.put("a", "10");

        Map<String, String> result = cTree.getHashTable(tree);
        assertEquals(expected, result);
    }

    @Test
    public void testGetHashTable_TreeIsNull() {
        Node tree = null;

        assertThrows(RuntimeException.class, () -> cTree.getHashTable(tree));
    }
}