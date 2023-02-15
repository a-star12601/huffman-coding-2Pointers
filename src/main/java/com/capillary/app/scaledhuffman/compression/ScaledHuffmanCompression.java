package com.capillary.app.scaledhuffman.compression;

import com.capillary.app.interfaces.compression.ICompression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * Class for root.general Tree-Based Encoders.
 */
public class ScaledHuffmanCompression implements ICompression {
    @Override
    public byte[] getHeader(Map<?,Integer> map) throws IOException {
        byte[] mapContent= getSerializedMap(map);
        byte[] header=(mapContent.length+"\n").getBytes();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( header );
        outputStream.write( mapContent );

        byte exportBytes[] = outputStream.toByteArray( );
        outputStream.close();

        return exportBytes;
    }

    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }

    @Override
    public List<Byte> getCompressedBytes(byte[] arr, Map<?,String> hash){
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
                    byteArr += hash.get(curWord);
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
            byteArr+=hash.get(curWord);
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
    public byte[] getSerializedMap(Map<?,Integer> map) throws IOException {
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        ObjectOutputStream serial=new ObjectOutputStream(bStream);

        serial.writeObject(map);
        serial.close();

        byte[] b= bStream.toByteArray();
        bStream.close();

        return b;
    }
}
