package com.capillary.app.hybridhuffman.compression;

import com.capillary.app.general.Node;
import com.capillary.app.general.NodeComparator;
import com.capillary.app.zipper.compression.ICompressionTree;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.*;
import java.util.PriorityQueue;
/**
 * Class for generating Word-Character-based Huffman Tree for encoding.
 */
public class HybridHuffmanCompressionTree implements ICompressionTree<String> {

    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }

    private long getFileSize(Map<String,Integer> map,Map<String,String> hash){
        long sum=0;
        for(Map.Entry<String,Integer> m: map.entrySet()){
            sum+=m.getValue()*hash.get(m.getKey()).length();
        }
        return (long) Math.ceil(sum/8);
    }

    private int getMapSize(Map<String, Integer> mp) {

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(mp);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return byteOutputStream.toByteArray().length;
    }

    private Map<String, Integer> generateDynamicMap(Map<String, Integer> sortedMap, double percentage){
        Map<String, Integer> mp = new LinkedHashMap<>();

        int limit = (int) Math.ceil(sortedMap.size() *  (percentage/100));
        int i=0;
        for (Map.Entry<String, Integer> m : sortedMap.entrySet()){
            if(i<limit){
                mp.put(m.getKey(), m.getValue());
                i++;
            }else{
                String k = m.getKey();
                if(k.length() > 1){
                    for(char c : k.toCharArray()){
                        mp.put(c+"", mp.getOrDefault(c+"", 0)+m.getValue());
                    }
                }else{
                    mp.put(k, mp.getOrDefault(k, 0)+m.getValue());
                }
            }
        }
//        System.out.println(mp.size());
        return mp;
    }

    private double probability(double f1, double f2, double temp) {
        if (f2 < f1) return 1;
        return Math.exp((f1 - f2) / temp);
    }

    private double SimulatedAnnealing(Map<String,Integer> mp) {
        double temperature = 1000;
        double coolingFactor = 0.2;

        double[] best = new double[]{Integer.MAX_VALUE, 0};
        double[] current = new double[]{Integer.MAX_VALUE, 0};

        Map<String,Integer> tempMap;
        Node tree;
        Map<String,String> hash;
        long mapSize=-1, fileSize=-1;

        while(temperature > 1){
            double[] randomArray = new double[2];
            double percentage = Math.random() * 100.0;

            tempMap = generateDynamicMap(mp, percentage);
            tree = generateTree(tempMap);
            hash = getHashTable(tree);

            fileSize= getFileSize(tempMap, hash);
            mapSize = getMapSize(tempMap);

            randomArray[0] = fileSize+mapSize;
            randomArray[1] = percentage;

            if(probability(current[0], randomArray[0], temperature) > Math.random()){
                current[0] = randomArray[0];
                current[1] = randomArray[1];
            }

            if(current[0] < best[0]){
                best[0] = current[0];
                best[1] = current[1];
            }

            temperature = temperature / (1 + coolingFactor * temperature);
        }
        return best[1];
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
        }

        double percentage = SimulatedAnnealing(sortedMap);
        Map<String,Integer> bestMap = generateDynamicMap(sortedMap, percentage);

        System.out.println(percentage);

        return bestMap;
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
