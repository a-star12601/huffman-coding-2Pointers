package com.capillary.app.lengthhuffman.compression;

import com.capillary.app.general.Node;
import com.capillary.app.general.NodeComparator;
import com.capillary.app.zipper.compression.ICompressionTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.*;
import java.util.PriorityQueue;
/**
 * Class for performing Huffman Encoding.
 */
public class LengthHuffmanCompressionTree implements ICompressionTree<String> {

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

        int count1=0, count2=0;
        int freq=0;
        double avg1, avg2;

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

        List<Map.Entry<String, Integer> > list = new LinkedList<>(map.entrySet());
        Collections.sort(
                list,
                (a, b) -> {
                    if(a.getValue() != b.getValue())
                        return b.getValue() - a.getValue();
                    return b.getKey().length() - a.getKey().length();
                });

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> l : list) {
            sortedMap.put(l.getKey(), l.getValue());
            count1 += l.getKey().length();
            count2 += l.getKey().length() * l.getValue();
            freq += l.getValue();
        }

        avg1 = count1 / sortedMap.size();
        avg2 = count2 / freq;

        System.out.println("Avg without Freq : " + avg1);
        System.out.println("Avg with Freq : " + avg2);

        Map<String, Integer> mp = new LinkedHashMap<>();

        int limit = (int) Math.ceil(sortedMap.size() * 0.2);
        int i=0;
        for (Map.Entry<String, Integer> m : sortedMap.entrySet()){
            if(i<limit){
                mp.put(m.getKey(), m.getValue());
                i++;
            }else{
                String k = m.getKey();
                if(k.length() > avg1 + 5){
                    mp.put(m.getKey(), m.getValue());
                }else{
                    if(k.length() > 1){
                        for(char c : k.toCharArray()){
                            mp.put(c+"", mp.getOrDefault(c+"", 0)+m.getValue());
                        }
                    }else{
                        mp.put(k, mp.getOrDefault(k, 0)+m.getValue());
                    }
                }
            }
        }

//        System.out.println(mp.size());
        return mp;
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
