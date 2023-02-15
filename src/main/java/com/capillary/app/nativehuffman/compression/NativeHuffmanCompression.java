package com.capillary.app.nativehuffman.compression;

import com.capillary.app.interfaces.compression.ICompression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for root.general Tree-Based Encoders.
 */
public class NativeHuffmanCompression implements ICompression {
    @Override
    public byte[] getHeader(HashMap<?,Integer> map) throws IOException {
        byte[] mapContent= getSerializedMap(map);
        byte[] header=(mapContent.length+"\n").getBytes();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( header );
        outputStream.write( mapContent );
        byte exportBytes[] = outputStream.toByteArray( );
        outputStream.close();
        return exportBytes;
    }

    @Override
    public List<Byte> getCompressedBytes(byte[] arr, HashMap<?,String> hash){
        String byteArr="";
        String currentByte="";
        List<Byte> bytes=new ArrayList<>();
        for (byte c : arr) {
            char ch = (char) c;
            byteArr+=hash.get(ch);
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
    public byte[] getSerializedMap(HashMap<?,Integer> map) throws IOException {
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        ObjectOutputStream serial=new ObjectOutputStream(bStream);
        serial.writeObject(map);
        serial.close();
        byte[] b= bStream.toByteArray();
        bStream.close();
        return b;
    }
}
