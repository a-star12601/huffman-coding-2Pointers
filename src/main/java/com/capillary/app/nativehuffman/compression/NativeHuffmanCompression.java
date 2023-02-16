package com.capillary.app.nativehuffman.compression;

import com.capillary.app.zipper.compression.ICompression;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for root.general Tree-Based Encoders.
 */
public class NativeHuffmanCompression implements ICompression<Character> {

    @Override
    public void writeObjects(Map<Character,Integer> map,byte arr[],String compressedFile) throws IOException {
        if(map == null || map.isEmpty()){
            throw new RuntimeException("Map is Null/Empty!!");
        }

        FileOutputStream fout = new FileOutputStream(compressedFile);
        ObjectOutputStream out =new ObjectOutputStream(fout);

        out.writeObject(map);
        out.writeObject(arr);

        out.close();
        fout.close();

    }

    @Override
    public List<Byte> getCompressedBytes(byte[] arr, Map<Character,String> hash){
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
