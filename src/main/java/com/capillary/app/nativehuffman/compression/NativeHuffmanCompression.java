package com.capillary.app.nativehuffman.compression;

import com.capillary.app.zipper.compression.ICompression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for root.general Tree-Based Encoders.
 */
public class NativeHuffmanCompression implements ICompression<Character> {

    @Override
    public List<Byte> getCompressedBytes(byte[] arr, Map<Character,String> hash){
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input byte array is Empty/ Null!!");
        }
        if(hash==null){
            throw new RuntimeException("Hash Table is Null!!");
        }

        String byteArr="";
        String currentByte="";
        List<Byte> bytes=new ArrayList<>();

        for (byte c : arr) {
            char ch = (char) c;
            byteArr += hash.get(ch);

            while(byteArr.length()>=8){
                currentByte=byteArr.substring(0,8);
                byteArr=byteArr.substring(8);
                byte b = (byte) Integer.parseInt(currentByte, 2);
                bytes.add(b);
            }
        }

        if(byteArr.length()>0){
            currentByte=String.format("%1$-" + 8 + "s", byteArr).replace(' ', '0');
            byte b = (byte) Integer.parseInt(currentByte, 2);
            bytes.add(b);
        }
        return bytes;
    }
}
