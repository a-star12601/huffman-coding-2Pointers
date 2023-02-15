package com.capillary.app.zipper;

import com.capillary.app.general.FileOperations;
import com.capillary.app.general.Node;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompression;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompressionTree;
import com.capillary.app.nativehuffman.decompression.NativeHuffmanDecompression;
import com.capillary.app.nativehuffman.decompression.NativeHuffmanDecompressionTree;
import com.capillary.app.interfaces.compression.ICompression;
import com.capillary.app.interfaces.compression.ICompressionTree;
import com.capillary.app.interfaces.decompression.IDecompression;
import com.capillary.app.interfaces.decompression.IDecompressionTree;
import com.capillary.app.scaledhuffman.compression.ScaledHuffmanCompression;
import com.capillary.app.scaledhuffman.compression.ScaledHuffmanCompressionTree;
import com.capillary.app.scaledhuffman.decompression.ScaledHuffmanDecompression;
import com.capillary.app.scaledhuffman.decompression.ScaledHuffmanDecompressionTree;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScaledHuffmanZipperUnzipper implements IZipperUnzipper{

    public boolean CheckFileNotEmpty(String filename){
        File f=new File(filename);
        return f.length()!=0;
    }

    public boolean CheckFileExists(String filename){
        File f=new File(filename);
        return f.exists();
    }

    public void compress(String originalFile, String compressedFile){
        try {
            FileOperations f = new FileOperations();
            byte[] inputBytes = f.readFile(originalFile);

            ICompressionTree cTree = new ScaledHuffmanCompressionTree();
            HashMap<String,Integer> map= (HashMap<String, Integer>) cTree.getFrequencyMap(inputBytes);
            Node tree=cTree.generateTree(map);
            HashMap<String,String> hash= (HashMap<String, String>) cTree.getHashTable(tree);

            double average=WAvg(map,hash);
            System.out.println("Average LPC : "+average);

            ICompression comp = new ScaledHuffmanCompression();
            List<Byte> encodedList = comp.getCompressedBytes(inputBytes,hash);
            byte[] headerContent = comp.getHeader(map);
            byte[] encodedBytes = f.byteFromByteList(encodedList);

            f.writeToFile(compressedFile, false, headerContent);
            f.writeToFile(compressedFile,true, encodedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decompress(String compressedFile) {
        try {
            FileOperations f = new FileOperations();
            byte[] compressBytes = f.readFile(compressedFile);

            IDecompressionTree dTree = new ScaledHuffmanDecompressionTree();
            HashMap<String,Integer> map= (HashMap<String, Integer>) dTree.getFrequencyMap(compressBytes);
            Node tree=dTree.regenerateTree(map);

            IDecompression decomp = new ScaledHuffmanDecompression();
            byte[] exportBytes=decomp.getDecompressedBytes(compressBytes,tree,dTree.getMapSize(),decomp.getCharCount(map));

            f.writeToFile("DEC"+compressedFile,false,exportBytes);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    double WAvg(HashMap<?,Integer> map,HashMap<?,String> hash){
        double sum=0;
        double chars=0;
        for(Map.Entry<?,Integer> m: map.entrySet()){
            sum+=m.getValue()*hash.get(m.getKey()).length();
            chars+=m.getKey().toString().length()*m.getValue();
        }
        return sum/chars;
    }
}
