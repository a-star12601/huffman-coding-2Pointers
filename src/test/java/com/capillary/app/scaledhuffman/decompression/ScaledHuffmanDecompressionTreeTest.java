package com.capillary.app.scaledhuffman.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.zipper.decompression.IDecompressionTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ScaledHuffmanDecompressionTreeTest {
    IDecompressionTree dTree;
    @Before
    public void setUp() throws Exception {
        dTree = new ScaledHuffmanDecompressionTree();
    }

    @After
    public void tearDown() throws Exception {
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
    public void testRegenerateTree_NormalCase() {
        Map<String, Integer> mp = new HashMap<>();
        mp.put("ab", 2);
        mp.put(" ", 2);
        mp.put("a", 1);

        Node child1=new Node("ab",2);
        Node child2=new Node(" ",2);
        Node child3=new Node("a",1);
        Node int1=new Node(child3,child2);
        Node expected=new Node(child1,int1);

        Node result = dTree.regenerateTree(mp);

        assertTrue(areIdentical(expected, result));
    }

    @Test
    public void testRegenerateTree_SingleNodeCase() {
        Map<String, Integer> mp = new HashMap<>();
        mp.put("ab", 3);

        Node child1=new Node("ab",3);
        Node child2=new Node();
        Node expected=new Node(child1,child2);

        Node result = dTree.regenerateTree(mp);

        assertTrue(areIdentical(expected, result));
    }

    @Test
    public void testRegenerateTree_MapIsNull() {
        Map<Character, Integer> mp = null;

        assertThrows(RuntimeException.class, () -> dTree.regenerateTree(mp));
    }

    @Test
    public void testRegenerateTree_MapIsEmpty() {
        Map<Character, Integer> mp = new HashMap<>();

        assertThrows(RuntimeException.class, () -> dTree.regenerateTree(mp));

    }

}