package com.capillary.app.scaledhuffman;

import com.capillary.app.general.FileOperations;
import com.capillary.app.general.Node;
import com.capillary.app.zipper.compression.ICompression;
import com.capillary.app.zipper.compression.ICompressionTree;
import com.capillary.app.zipper.decompression.IDecompression;
import com.capillary.app.zipper.decompression.IDecompressionTree;
import com.capillary.app.scaledhuffman.compression.ScaledHuffmanCompression;
import com.capillary.app.scaledhuffman.compression.ScaledHuffmanCompressionTree;
import com.capillary.app.scaledhuffman.decompression.ScaledHuffmanDecompression;
import com.capillary.app.scaledhuffman.decompression.ScaledHuffmanDecompressionTree;
import com.capillary.app.zipper.IZipperUnzipper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ScaledHuffmanZipperUnzipper implements IZipperUnzipper {

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
            Map<String,Integer> map= cTree.getFrequencyMap(inputBytes);
            Node tree=cTree.generateTree(map);
            Map<String,String> hash= cTree.getHashTable(tree);

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

    public void decompress(String compressedFile, String decompressedFile) {
        try {
            FileOperations f = new FileOperations();
            byte[] compressBytes = f.readFile(compressedFile);

            IDecompressionTree dTree = new ScaledHuffmanDecompressionTree();
            Map<String,Integer> map= dTree.getFrequencyMap(compressBytes);
            Node tree=dTree.regenerateTree(map);

            IDecompression decomp = new ScaledHuffmanDecompression();
            byte[] exportBytes=decomp.getDecompressedBytes(compressBytes,tree,dTree.getMapSize(),decomp.getCharCount(map));

            f.writeToFile(decompressedFile,false,exportBytes);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    double WAvg(Map<String,Integer> map,Map<String,String> hash){
        double sum=0;
        double chars=0;
        for(Map.Entry<String,Integer> m: map.entrySet()){
            sum+=m.getValue()*hash.get(m.getKey()).length();
            chars+=m.getKey().toString().length()*m.getValue();
        }
        return sum/chars;
    }
}
