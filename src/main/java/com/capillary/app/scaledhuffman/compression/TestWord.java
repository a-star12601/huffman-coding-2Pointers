//package com.capillary.app.scaledhuffman.compression;
//
//import com.capillary.app.general.FileOperations;
//import com.capillary.app.general.Node;
//import com.capillary.app.scaledhuffman.decompression.ScaledHuffmanDecompressionTree;
//import com.capillary.app.scaledhuffman.decompression.ScaledHuffmanDecompression;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class TestWord {
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String compressed="a.txt";
//        Instant inst1 = Instant.now();
//        ScaledHuffmanCompressionTree enc=new ScaledHuffmanCompressionTree();
//        FileOperations f=new FileOperations();
//        byte[] arr=f.readFile("pg100.txt");
//        HashMap<String ,Integer> map=enc.getFrequencyMap(arr);
//        System.out.println(map.size());
//        Node tree=enc.generateTree(map);
//        //      System.out.println("Creating HashTable...");
//        HashMap<String,String> hash= (HashMap<String, String>)enc.getHashTable(tree);
//        double average=WAvg(map,hash);
//        System.out.println("Average LPC:"+average);
//        ScaledHuffmanCompression encoder=new ScaledHuffmanCompression();
//        //      System.out.println("Writing Map to File...");
//        List<Byte> encodedList=encoder.getCompressedBytes(arr,hash);
////          System.out.println("Compressing to File...");
//        byte[] headerContent=encoder.getHeader(map);
//        byte[] encodedBytes=f.byteFromByteList(encodedList);
//        f.writeToFile(compressed, false,headerContent);
//        f.writeToFile(compressed,true,encodedBytes);
//        Instant inst2 = Instant.now();
//        System.out.println("Time Taken for Compression: "+ Duration.between(inst1, inst2).toString());
//        f.compressionStats("pg100.txt", compressed);
//
//
//        ScaledHuffmanDecompressionTree dec=new ScaledHuffmanDecompressionTree();
//        ScaledHuffmanDecompression decoder=new ScaledHuffmanDecompression();
//        Instant inst3 = Instant.now();
//        //        System.out.println("Reading Frequency Map...");
//        byte[] compressBytes=f.readFile(compressed);
//        HashMap<String,Integer> Cmap= (HashMap<String, Integer>) dec.getFrequencyMap(compressBytes);
////            System.out.println("Creating Huffman Tree...");
//        Node Ctree=dec.regenerateTree(Cmap);
//        //        System.out.println("Decompressing...");
//        byte[] exportBytes=decoder.getDecompressedBytes(compressBytes,tree,dec.getMapSize(),decoder.getCharCount(Cmap));
//        f.writeToFile("DEC.txt",false,exportBytes);
//        Instant inst4 = Instant.now();
//        System.out.println("Time Taken for Decompression: "+ Duration.between(inst3, inst4).toString());
//        System.out.println("Comparing Files...");
//        if(f.compareFiles("pg100.txt","DEC.txt")){
//            System.out.println("Files Matched");
//        }
//        else {
//            System.out.println("Files Mismatched");
//        }
//
//
//    }
//    static double WAvg(HashMap<?,Integer> map,HashMap<?,String> hash){
//        double sum=0;
//        double chars=0;
//        for(Map.Entry<?,Integer> m: map.entrySet()){
//            sum+=m.getValue()*hash.get(m.getKey()).length();
//            chars+=m.getKey().toString().length()*m.getValue();
//        }
//        return sum/chars;
//    }
//}
