package com.capillary.app.wordapproach.decompression;

import com.capillary.app.general.Node;
import com.capillary.app.interfaces.decompression.IDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for root.general Tree-Based Decoders.
 */
public class WordTreeDecoder implements IDecoder {
    /**
     * variable storing size of serialised map.
     */
    long mapsize;

    @Override
    public int getCount(HashMap<?,Integer> map) {
        int count=0;
        for(Map.Entry<?, Integer> entry:map.entrySet()){
//            System.out.println(entry.getKey()+" | "+entry.getValue());
            count+=entry.getKey().toString().length() * entry.getValue();
        }
        return count;
        //System.out.println(count);
    }

    public byte[] decodingLogic(byte[] arr,Node tree,long mapsize,long count){
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
            while(root.Left!=null && root.Right!=null){
                if(bitcounter==8){
                    b=arr[++curbyte];
                    for (int i = 0; i < 8; i++)
                        bits[7 - i] = ((b & (1 << i)) != 0);
                    bitcounter=0;
                }
                else if(!bits[bitcounter]) {
                    bitcounter++;
                    root = root.Left;
                }
                else{
                    bitcounter++;
                    root = root.Right;
                }
            }
            byte[] currentWord=root.Char.getBytes();
            if(currentWord.length==1){
                bytes[chars++]= (byte) root.Char.charAt(0);
            }
            else
            for(byte c:currentWord) {
                bytes[chars++] = c;
            }
            if(chars==count){break;}
            root= tree;
        }
        return bytes;
    }


}

