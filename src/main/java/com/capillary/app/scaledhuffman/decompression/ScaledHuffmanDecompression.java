package com.capillary.app.scaledhuffman.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.interfaces.decompression.IDecompression;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for root.general Tree-Based Decoders.
 */
public class ScaledHuffmanDecompression implements IDecompression {

    @Override
    public int getCharCount(HashMap<?,Integer> map) {
        int count=0;
        for(Map.Entry<?, Integer> entry:map.entrySet()){
            count+=entry.getKey().toString().length() * entry.getValue();
        }
        return count;
    }

    public byte[] getDecompressedBytes(byte[] arr, Node tree, long mapsize, long count){
        byte[] bytes=new byte[(int) count];
        int curbyte=(int)mapsize;
        Node root= tree;
        byte b=arr[curbyte];
        int chars=0;
        int bitcounter=0;
        boolean[] bits = new boolean[8];
        for (int i = 0; i < 8; i++)
            bits[7 - i] = ((b & (1 << i)) != 0);
        while(curbyte<arr.length){
            while(root.leftNode !=null && root.rightNode !=null){
                if(bitcounter==8){
                    b=arr[++curbyte];
                    for (int i = 0; i < 8; i++)
                        bits[7 - i] = ((b & (1 << i)) != 0);
                    bitcounter=0;
                }
                else if(!bits[bitcounter]) {
                    bitcounter++;
                    root = root.leftNode;
                }
                else{
                    bitcounter++;
                    root = root.rightNode;
                }
            }
            byte[] currentWord=root.value.getBytes();
            if(currentWord[0]<0){
                bytes[chars++]= (byte) root.value.charAt(0);
            }
            else
                for(byte c:currentWord) {
                    bytes[chars++] = c;
                }
            if(chars==count){
                break;}
            root= tree;
        }
        return bytes;
    }
}
