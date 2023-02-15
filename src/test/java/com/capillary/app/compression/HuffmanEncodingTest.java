package com.capillary.app.compression;

import com.capillary.app.general.Node;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompressionTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;

public class HuffmanEncodingTest {
    private static NativeHuffmanCompressionTree enc;


    @Before
    public void setup(){

    }


    @Test
    public void testInitialiseMap() throws IOException, ClassNotFoundException {
        enc=new NativeHuffmanCompressionTree();
        byte arr[]=new byte[]{97,97,97,97,97,97,97,97,97,97,97,97,97,97};
        HashMap<Character,Integer> ActualMap=enc.getFrequencyMap(arr);
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',14);
        Assert.assertEquals("Map Doesn't Match",ActualMap, map);
    }
    @Test
    public void TestMapNullFile(){
        byte[] arr=null;
        enc=new NativeHuffmanCompressionTree();
        assertThrows(RuntimeException.class,()->{HashMap<Character,Integer> ActualMap=enc.getFrequencyMap(arr);});
    }
    @Test
    public void TestMapEmptyFile(){
        byte[] arr=new byte[0];
        enc=new NativeHuffmanCompressionTree();
        assertThrows(RuntimeException.class,()->{HashMap<Character,Integer> ActualMap=enc.getFrequencyMap(arr);});
    }


    @Test
    public void testInitialiseTreeForSingleNode() {
        enc=new NativeHuffmanCompressionTree();
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',88);
        Node ActualTree=enc.generateTree(map);
        Node tree=new Node(new Node('a',88),new Node(),1);
        //System.out.println(enc.tree.Left);
        Assert.assertTrue(MatchTrees(ActualTree, tree));
    }
    @Test
    public void TestInitialiseTreeNullNode(){
        enc=new NativeHuffmanCompressionTree();
        Node tree=new Node();
        assertThrows(RuntimeException.class,()->enc.generateTree(null));
    }
    @Test
    public void TestInitialiseTreeEmptyMap(){
        enc=new NativeHuffmanCompressionTree();
        Node tree=new Node();
        assertThrows(RuntimeException.class,()->enc.generateTree(new HashMap<Character,Integer>()));
    }
    @Test
    public void testInitialiseTreeForMultipleNodes() {
        enc=new NativeHuffmanCompressionTree();
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',4);
        map.put('b',2);
        Node ActualTree=enc.generateTree(map);
        Node tree=new Node(new Node('b',2),new Node('a',4),1);
        //System.out.println(enc.tree.Left);
        Assert.assertTrue(MatchTrees(ActualTree, tree));
    }


    @Test
    public void testGenerateTreeMap() {
        enc=new NativeHuffmanCompressionTree();
        Node tree=new Node(new Node('b',29),new Node(new Node('a',21),new Node('c',22),1),2);
        HashMap<Character,String> ActualHash=enc.getHashTable(tree);
        HashMap<Character,String > hash=new HashMap<>();
        hash.put('b',"0");
        hash.put('a',"10");
        hash.put('c',"11");
        Assert.assertEquals("Hashmaps not Equal", ActualHash, hash);
    }

//    @Test
//    public void checkOverallEncode() throws IOException {
//            HashMap<Character,Integer> map=enc.initialiseMap(inputBytes);
//            Node tree=enc.initialiseTree(map);
//            HashMap<Character,String> hash=enc.generateTreeMap(tree);
//    }

    public boolean MatchTrees(Node expected,Node actual){
        if(expected==null && actual==null)
        {return true;}
        if(expected.value ==actual.value && expected.freq ==actual.freq && expected.Height==actual.Height)
        return MatchTrees(expected.leftNode,actual.leftNode)&&MatchTrees(expected.rightNode,actual.rightNode);
        else return false;
    }
    public void preorder(Node tree){
        if(tree!=null){
            System.out.println(tree.value);
            preorder(tree.leftNode);
            preorder(tree.rightNode);
        }
    }
}