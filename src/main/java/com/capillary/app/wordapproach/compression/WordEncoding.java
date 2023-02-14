package com.capillary.app.wordapproach.compression;

import com.capillary.app.general.Node;
import com.capillary.app.general.Sort;
import com.capillary.app.interfaces.compression.IEncodeTree;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * Class for performing Huffman Encoding.
 */
public class WordEncoding implements IEncodeTree {

    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }
    @Override
    public HashMap<String,Integer> initialiseMap(byte[] arr){
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
//        for(Map.Entry<?, Integer> entry:map.entrySet()){
//            System.out.println(entry.getKey()+" | "+entry.getValue());
//        }
        return map;
    }
    @Override
    public Node initialiseTree(HashMap<?, Integer> map) {
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
            root=new Node(single,new Node(),1);
        }
        while(q.size()>1) {
            Node left=q.poll();
            Node right=q.poll();
            Node sum=new Node(left,right,Math.max(left.Height,right.Height)+1);
            root=sum;
            q.add(sum);
        }
        tree=root;
        return tree;
    }
    public void setBitsHash(Node tree, String bits, HashMap<String,String> freqMap) {
        if(tree ==null){
            //do nothing
        }
        else if(tree.Left==null && tree.Right==null) {
            freqMap.put( tree.Char,bits);
        }
        else {
            setBitsHash(tree.Left,bits+"0",freqMap);
            setBitsHash(tree.Right,bits+"1",freqMap);
        }
    }

    @Override
    public HashMap< String,String> generateTreeMap(Node tree) {
        HashMap<String,String > hash=new HashMap<>();
        setBitsHash(tree,"",hash);

//        for(Map.Entry<String, String> e : hash.entrySet()) {
//            System.out.println(e.getKey()+" | "+e.getValue());
//        }
        return hash;

    }


}
