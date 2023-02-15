package com.capillary.app.scaledhuffman.compression;

import com.capillary.app.general.Node;
import com.capillary.app.general.Sort;
import com.capillary.app.interfaces.compression.ICompressionTree;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * Class for performing Huffman Encoding.
 */
public class ScaledHuffmanCompressionTree implements ICompressionTree {

    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }
    @Override
    public HashMap<String,Integer> getFrequencyMap(byte[] arr){
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input file is Empty");
        }
        HashMap<String,Integer> map=new HashMap<>();
        String curWord="";
        int count=0;
        for (byte b : arr) {
            char ch = (char) b;
            if(isLetterOrDigit(ch)){
                curWord+=ch;
            }
            else{
                if(curWord!=""){
                    count = map.getOrDefault(curWord, 0);
                    map.put(curWord, count + 1);
                    curWord="";
                }
                count = map.getOrDefault(ch+"", 0);
                map.put(ch+"", count + 1);
            }
            }
        if(curWord!=""){
            count = map.getOrDefault(curWord, 0);
            map.put(curWord, count + 1);
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
        tree=root;
        return tree;
    }
    public void setBitsHash(Node tree, String bits, HashMap<String,String> freqMap) {
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
    public HashMap< String,String> getHashTable(Node tree) {
        HashMap<String,String > hash=new HashMap<>();
        setBitsHash(tree,"",hash);

        return hash;
    }
}
