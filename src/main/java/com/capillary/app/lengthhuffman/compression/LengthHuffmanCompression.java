package com.capillary.app.lengthhuffman.compression;

import com.capillary.app.zipper.compression.ICompression;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * Class for root.general Tree-Based Encoders.
 */
public class LengthHuffmanCompression implements ICompression<String> {
    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }

    @Override
    public List<Byte> getCompressedBytes(byte[] arr, Map<String,String> hash){
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input byte array is Empty/ Null!!");
        }
        if(hash==null){
            throw new RuntimeException("Hash Table is Null!!");
        }

        String byteArr="";
        String currentByte="";
        String curWord="";
        List<Byte> bytes=new ArrayList<>();

        for (byte c : arr) {
            char ch = (char) c;
            if(isLetterOrDigit(ch)){
                curWord+=ch;
            }
            else {
                if (curWord != "") {
                    if(hash.containsKey(curWord))
                        byteArr += hash.get(curWord);
                    else{
                        for(char cur:curWord.toCharArray()){
                            byteArr += hash.get(cur+"");
                        }
                    }
                    curWord = "";
                }
                byteArr += hash.get(ch+"");

                while(byteArr.length()>=8){
                    currentByte=byteArr.substring(0,8);
                    byteArr=byteArr.substring(8);
                    byte b = (byte) Integer.parseInt(currentByte, 2);
                    bytes.add(b);
                }
            }
        }

        if(curWord!=""){
            if(hash.containsKey(curWord))
                byteArr += hash.get(curWord);
            else{
                for(char cur:curWord.toCharArray()){
                    byteArr += hash.get(cur+"");
                }
            }
        }

        while(byteArr.length()>=8){
            currentByte=byteArr.substring(0,8);
            byteArr=byteArr.substring(8);
            byte b = (byte) Integer.parseInt(currentByte, 2);
            bytes.add(b);
        }

        if(byteArr.length()>0){
            currentByte=String.format("%1$-" + 8 + "s", byteArr).replace(' ', '0');
            byte b = (byte) Integer.parseInt(currentByte, 2);
            bytes.add(b);
        }
        return bytes;
    }
}
