package com.capillary.app.scaledhuffman.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.general.Sort;
import com.capillary.app.interfaces.decompression.IDecompressionTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Class for performing Huffman decoding.
 */
public class ScaledHuffmanDecompressionTree implements IDecompressionTree {

    int mapsize=0;
    public long getMapSize(){
        return mapsize;
    }
    @Override
    public HashMap<String,Integer> getFrequencyMap(byte[] arr) throws IOException, ClassNotFoundException {
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input file is Empty");
        }
        HashMap<String,Integer> map=new HashMap<>();
        int i=0;
        for(byte x:arr){
            if((char) x=='\n'){
                break;
            }
            else
                mapsize=mapsize*10+Integer.parseInt((char)x+"");
            i++;
        }
        mapsize=mapsize+i+1;
        //System.out.println(mapsize);
        byte[] b1= Arrays.copyOfRange(arr,i+1,(int)mapsize);
        ByteArrayInputStream bStream=new ByteArrayInputStream(b1);
        ObjectInputStream serial=new ObjectInputStream(bStream);
        map=(HashMap<String, Integer>) serial.readObject();
        serial.close();
        bStream.close();
//        for(Map.Entry<String, Integer> e : map.entrySet()) {
//            System.out.println(e.getKey()+" | "+e.getValue());
//        }
        return map;
    }
    @Override
    public Node regenerateTree(HashMap<?, Integer> map) {
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

}
