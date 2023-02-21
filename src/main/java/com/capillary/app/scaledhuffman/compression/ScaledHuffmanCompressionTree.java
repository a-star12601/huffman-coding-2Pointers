package com.capillary.app.scaledhuffman.compression;

import com.capillary.app.general.Node;
import com.capillary.app.general.NodeComparator;
import com.capillary.app.zipper.compression.ICompressionTree;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * Class for generating Word-based Huffman Tree for encoding.
 */
public class ScaledHuffmanCompressionTree implements ICompressionTree<String> {

    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }

    @Override
    public Map<String,Integer> getFrequencyMap(byte[] arr){
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input file is Empty");
        }

        Map<String,Integer> map=new HashMap<>();
        String curWord="";

        for (byte b : arr) {
            char ch = (char) b;
            if(isLetterOrDigit(ch)){
                curWord+=ch;
            }
            else{
                if(curWord!=""){
                    map.put(curWord, map.getOrDefault(curWord, 0) + 1);
                    curWord="";
                }
                map.put(ch+"", map.getOrDefault(ch+"", 0) + 1);
            }
            }
        if(curWord!=""){
            map.put(curWord, map.getOrDefault(curWord, 0) + 1);
        }
        return map;
    }

    @Override
    public Node generateTree(Map<String, Integer> map) {
        if(map==null || map.size()==0){
            throw new RuntimeException("Map is empty!!");
        }

        PriorityQueue<Node> q=new PriorityQueue<>(new NodeComparator());
        for(Map.Entry<String, Integer> entry:map.entrySet()) {
            Node temp=new Node( entry.getKey().toString(),entry.getValue());
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
        return root;
    }
    private void setBitsHash(Node tree, String bits, Map<String,String> freqMap) {
        if(tree !=null){
            if(tree.leftNode ==null && tree.rightNode ==null) {
                freqMap.put( tree.value,bits);
            }
            else {
                setBitsHash(tree.leftNode,bits+"0",freqMap);
                setBitsHash(tree.rightNode,bits+"1",freqMap);
            }
        }
    }

    @Override
    public Map< String,String> getHashTable(Node tree) {
        if(tree==null){
            throw new RuntimeException("Tree is Null!!");
        }

        Map<String,String > hash=new HashMap<>();
        setBitsHash(tree,"",hash);

        return hash;
    }
}
