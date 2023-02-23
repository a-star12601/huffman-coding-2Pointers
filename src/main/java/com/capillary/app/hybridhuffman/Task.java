package com.capillary.app.hybridhuffman;

import com.capillary.app.general.Node;
import com.capillary.app.general.NodeComparator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;

public class Task implements Callable<Integer> {
    Map<String,Integer> mp;
    int r;

    public Task(Map<String,Integer> _mp, int _r){
        mp = _mp;
        r = _r;
    }

    Map<String,Integer> tempMap;
    Map<String,String> hashTable;
    Node tree;
    long totalSize;

    Map<String,Integer> bestMap;
    long bestSize = Integer.MAX_VALUE;
    int bestPercent;

    @Override
    public Integer call() throws Exception {
        for (int i=r*25; i<=(r+1)*25; i++){
            generateMap(i);
        }
        return Math.toIntExact(bestSize);
    }

    public void generateMap(int i){
        tempMap = generateDynamicMap(mp, i);
        tree = generateTree(tempMap);
        hashTable = getHashTable(tree);
        totalSize = getFileSize(tempMap, hashTable) + getMapSize(tempMap);

        if(totalSize < bestSize){
            bestSize = totalSize;
            bestMap = tempMap;
            bestPercent=i;
        }
    }

    private static Map<String, Integer> generateDynamicMap(Map<String, Integer> sortedMap, int percentage){
        Map<String, Integer> mp = new LinkedHashMap<>();

        int limit = (int) Math.ceil(sortedMap.size() *  ((double) percentage/100));
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
        return mp;
    }

    private static long getFileSize(Map<String,Integer> map,Map<String,String> hash){
        long sum=0;
        for(Map.Entry<String,Integer> m: map.entrySet()){
            sum+=m.getValue()*hash.get(m.getKey()).length();
        }
        return (long) Math.ceil(sum/8);
    }

    private static int getMapSize(Map<String, Integer> mp) {

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

    private static Node generateTree(Map<String, Integer> map) {
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

    private static void setBitsHash(Node tree, String bits, Map<String,String> freqMap) {
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

    private static Map< String,String> getHashTable(Node tree) {
        if (tree == null) {
            throw new RuntimeException("Tree is Null!!");
        }

        Map<String, String> hash = new HashMap<>();
        setBitsHash(tree, "", hash);

        return hash;
    }
}
