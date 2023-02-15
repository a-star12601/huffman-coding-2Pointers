package com.capillary.app.nativehuffman.compression;

import com.capillary.app.general.Node;
import com.capillary.app.general.NodeComparator;
import com.capillary.app.interfaces.compression.ICompressionTree;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * Class for performing Huffman Encoding.
 */
public class NativeHuffmanCompressionTree implements ICompressionTree {

    @Override
    public Map<Character,Integer> getFrequencyMap(byte[] arr){
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input byte array is Empty!!");
        }

        Map<Character,Integer> map = new HashMap<>();
        for (byte b : arr) {
            char ch = (char) b;
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        return map;
    }

    @Override
    public Node generateTree(Map<?, Integer> map) {
        if(map==null || map.size()==0){
            throw new RuntimeException("Map is empty!!");
        }

        PriorityQueue<Node> q=new PriorityQueue<>(new NodeComparator());

        for(Map.Entry<?, Integer> entry:map.entrySet()) {
            Node temp=new Node(entry.getKey().toString(),entry.getValue());
            q.add(temp);
        }

        Node root=null;
        if(q.size() == 1){
            Node single = q.poll();
            root = new Node(single,new Node());
        }
        while(q.size()>1) {
            Node left=q.poll();
            Node right=q.poll();
            Node sum=new Node(left, right);
            root = sum;
            q.add(sum);
        }
        return root;
    }
    public void setBitsHash(Node tree, String bits, Map<Character,String> freqMap) {
        if(tree !=null){
            if(tree.leftNode ==null && tree.rightNode ==null) {
                freqMap.put(tree.value.charAt(0),bits);
            }
            else {
                setBitsHash(tree.leftNode,bits+"0",freqMap);
                setBitsHash(tree.rightNode,bits+"1",freqMap);
            }
        }
    }

    @Override
    public Map< Character,String> getHashTable(Node tree) {
        Map<Character,String > hash=new HashMap<>();
        setBitsHash(tree,"",hash);

        return hash;
    }
}
