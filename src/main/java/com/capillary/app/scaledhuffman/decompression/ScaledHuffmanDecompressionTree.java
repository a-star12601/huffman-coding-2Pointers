package com.capillary.app.scaledhuffman.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.general.NodeComparator;
import com.capillary.app.zipper.decompression.IDecompressionTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Class for generating Word-based Huffman Tree for decoding.
 */
public class ScaledHuffmanDecompressionTree implements IDecompressionTree<String> {


    @Override
    public Node regenerateTree(Map<String, Integer> map) {
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

}
