package com.capillary.app.nativehuffman;

import com.capillary.app.general.FileOperations;
import com.capillary.app.general.Node;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompression;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompressionTree;
import com.capillary.app.nativehuffman.decompression.NativeHuffmanDecompression;
import com.capillary.app.nativehuffman.decompression.NativeHuffmanDecompressionTree;
import com.capillary.app.zipper.compression.ICompression;
import com.capillary.app.zipper.compression.ICompressionTree;
import com.capillary.app.zipper.decompression.IDecompression;
import com.capillary.app.zipper.decompression.IDecompressionTree;
import com.capillary.app.zipper.IZipperUnzipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

public class NativeHuffmanZipperUnzipper implements IZipperUnzipper {

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

            ICompressionTree cTree = new NativeHuffmanCompressionTree();
            Map<Character,Integer> map= cTree.getFrequencyMap(inputBytes);
            Node tree=cTree.generateTree(map);
            Map<Character,String> hash= cTree.getHashTable(tree);

            double average=WAvg(map,hash);
            System.out.println("Average LPC : "+average);

            ICompression comp = new NativeHuffmanCompression();
            List<Byte> encodedList = comp.getCompressedBytes(inputBytes,hash);
            byte[] encodedBytes = f.byteFromByteList(encodedList);
            comp.writeObjects(map,encodedBytes,compressedFile);
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decompress(String compressedFile, String decompressedFile) {
        try {
            FileOperations f = new FileOperations();
            IDecompressionTree dTree = new NativeHuffmanDecompressionTree();

            FileInputStream fin = new FileInputStream(compressedFile);
            ObjectInputStream in = new ObjectInputStream(fin);

            Map<Character,Integer> map= (Map<Character, Integer>) in.readObject();
            byte[] compressBytes = (byte[]) in.readObject();

            in.close();
            fin.close();


            Node tree=dTree.regenerateTree(map);

            IDecompression decomp = new NativeHuffmanDecompression();
            byte[] exportBytes=decomp.getDecompressedBytes(compressBytes,tree,decomp.getCharCount(map));

            f.writeToFile(decompressedFile,false,exportBytes);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    double WAvg(Map<Character,Integer> map,Map<Character,String> hash){
        double sum=0;
        double chars=0;
        for(Map.Entry<Character,Integer> m: map.entrySet()){
            sum+=m.getValue()*hash.get(m.getKey()).length();
            chars+=m.getValue();
        }
        return sum/chars;
    }
}
