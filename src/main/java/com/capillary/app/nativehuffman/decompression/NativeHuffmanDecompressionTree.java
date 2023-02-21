package com.capillary.app.nativehuffman.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.general.NodeComparator;
import com.capillary.app.zipper.decompression.IDecompressionTree;

import java.util.Map;
import java.util.PriorityQueue;

/**
 * Class for generating Character-based Huffman Tree for decoding.
 */
public class NativeHuffmanDecompressionTree implements IDecompressionTree<Character> {

    @Override
    public Node regenerateTree(Map<Character, Integer> map) {
        if(map==null||map.size()==0){
            throw new RuntimeException("Map is empty!!");
        }

        PriorityQueue<Node> q=new PriorityQueue<>(map.size(), new NodeComparator());
        for(Map.Entry<Character, Integer> entry:map.entrySet()) {
            Node temp=new Node(entry.getKey().toString(),entry.getValue());
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
