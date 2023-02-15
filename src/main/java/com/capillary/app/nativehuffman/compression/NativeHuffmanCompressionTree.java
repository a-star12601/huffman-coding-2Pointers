package com.capillary.app.nativehuffman.compression;

import com.capillary.app.general.Node;
import com.capillary.app.general.Sort;
import com.capillary.app.interfaces.compression.ICompressionTree;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * Class for performing Huffman Encoding.
 */
public class NativeHuffmanCompressionTree implements ICompressionTree {

    @Override
    public HashMap<Character,Integer> getFrequencyMap(byte[] arr){
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input byte array is Empty!!");
        }
        HashMap<Character,Integer> map=new HashMap<>();
        for (byte b : arr) {
            char ch = (char) b;
            int count = map.getOrDefault(ch, 0);
            map.put(ch, count + 1);
        }
        return map;
    }

    @Override
    public Node generateTree(HashMap<?, Integer> map) {
        if(map==null||map.size()==0){
            throw new RuntimeException("Map is empty!!");
        }
        Node tree;
        PriorityQueue<Node> q=new PriorityQueue<>(new Sort());
        for(Map.Entry<?, Integer> entry:map.entrySet()) {
            Node temp=new Node(entry.getKey().toString(),entry.getValue());
            q.add(temp);
        }
        Node root=null;
        if(q.size()==1){
            Node single=q.poll();
            root=new Node(single,new Node());
        }
        while(q.size()>1) {
            Node left=q.poll();
            Node right=q.poll();
            Node sum=new Node(left,right);
            root=sum;
            q.add(sum);
        }
        tree=root;
        return tree;
    }
    public void setBitsHash(Node tree, String bits, HashMap<Character,String> freqMap) {
        if(tree !=null){
            if(tree.leftNode ==null && tree.rightNode ==null) {
                freqMap.put((char) tree.value.charAt(0),bits);
            }
            else {
                setBitsHash(tree.leftNode,bits+"0",freqMap);
                setBitsHash(tree.rightNode,bits+"1",freqMap);
            }
        }
    }

    @Override
    public HashMap< Character,String> getHashTable(Node tree) {
        HashMap<Character,String > hash=new HashMap<>();
        setBitsHash(tree,"",hash);

//        for(Map.Entry<Character, String> e : hash.entrySet()) {
//            System.out.println(e.getKey()+" | "+e.getValue());
//        }
        return hash;

    }


}
