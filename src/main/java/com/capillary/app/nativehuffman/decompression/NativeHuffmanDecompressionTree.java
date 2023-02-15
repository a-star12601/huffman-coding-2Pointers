package com.capillary.app.nativehuffman.decompression;

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
public class NativeHuffmanDecompressionTree implements IDecompressionTree {

    int mapsize=0;

    public long getMapSize(){
        return mapsize;
    }

    @Override
    public HashMap<Character,Integer> getFrequencyMap(byte[] arr) throws IOException, ClassNotFoundException {
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input file is Empty");
        }
        HashMap<Character,Integer> map;
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
        byte[] b1= Arrays.copyOfRange(arr,i+1,(int)mapsize);
        ByteArrayInputStream bStream=new ByteArrayInputStream(b1);
        ObjectInputStream serial=new ObjectInputStream(bStream);
        map=(HashMap<Character, Integer>) serial.readObject();
        serial.close();
        bStream.close();
        return map;
    }
    @Override
    public Node regenerateTree(HashMap<?, Integer> map) {
        Node tree=new Node();
        if(map==null||map.size()==0){
            throw new RuntimeException("Map is empty!!");
        }
        PriorityQueue<Node> q=new PriorityQueue<>(map.size(),new Sort());
        for(Map.Entry<?, Integer> entry:map.entrySet()) {
            Node temp=new Node(entry.getKey().toString(),entry.getValue());
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
