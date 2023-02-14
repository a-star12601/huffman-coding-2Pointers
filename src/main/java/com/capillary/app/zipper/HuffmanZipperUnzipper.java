package com.capillary.app.zipper;

import com.capillary.app.general.FileOperations;
import com.capillary.app.general.Node;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanZipperUnzipper implements IZipperUnzipper{

    public boolean CheckFileNotEmpty(String filename){
        File f=new File(filename);
        return f.length()!=0;
    }

    public boolean CheckFileExists(String filename){
        File f=new File(filename);
        return f.exists();
    }

    public void encode(PassableObject object){
        try {
            FileOperations f=new FileOperations();
            Instant inst1 = Instant.now();
            //      System.out.println("Creating Frequency Map...");
            byte[] inputBytes=f.readFile(object.original);
            HashMap<Character,Integer> map= (HashMap<Character, Integer>) object.enc.initialiseMap(inputBytes);
            //      System.out.println("Creating Huffman Tree...");
            Node tree=object.enc.initialiseTree(map);
            //      System.out.println("Creating HashTable...");
            HashMap<Character,String> hash= (HashMap<Character, String>) object.enc.generateTreeMap(tree);
            double average=WAvg(map,hash);
            System.out.println("Average LPC:"+average);

            //      System.out.println("Writing Map to File...");
            List<Byte> encodedList= object.encoder.encodingLogic(inputBytes,hash);
//          System.out.println("Compressing to File...");
            byte[] headerContent=object.encoder.storeMap(map);
            byte[] encodedBytes=f.byteFromByteList(encodedList);
            f.writeToFile(object.compressed, false,headerContent);
            f.writeToFile(object.compressed,true,encodedBytes);
            Instant inst2 = Instant.now();
            System.out.println("Time Taken for Compression: "+ Duration.between(inst1, inst2).toString());
            f.compressionStats(object.original, object.compressed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decode(PassableObject object) {
        try {
            FileOperations f=new FileOperations();
            Instant inst1 = Instant.now();
            //        System.out.println("Reading Frequency Map...");
            byte[] compressBytes=f.readFile(object.compressed);
            byte[] exportBytes=decodeAbstract(object,compressBytes);
            System.out.println(exportBytes[0]+" "+exportBytes.length);
            f.writeToFile("DEC"+object.original,false,exportBytes);
            Instant inst2 = Instant.now();
            System.out.println("Time Taken for Decompression: "+ Duration.between(inst1, inst2).toString());
            System.out.println("Comparing Files...");
            if(f.compareFiles(object.original,"DEC"+object.original)){
                System.out.println("Files Matched");
            }
            else {
                System.out.println("Files Mismatched");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    byte[] decodeAbstract(PassableObject object,byte[] compressBytes) throws IOException, ClassNotFoundException {
        HashMap<Character,Integer> map= (HashMap<Character, Integer>) object.dec.initialiseMap(compressBytes);
//            System.out.println("Creating Huffman Tree...");
        Node tree=object.dec.initialiseTree(map);
        //        System.out.println("Decompressing...");
        byte[] exportBytes=object.decoder.decodingLogic(compressBytes,tree,object.dec.getMapSize(),object.decoder.getCount(map));
        return exportBytes;
    }

    double WAvg(HashMap<?,Integer> map,HashMap<?,String> hash){
        double sum=0;
        double chars=0;
        for(Map.Entry<?,Integer> m: map.entrySet()){
            sum+=m.getValue()*hash.get(m.getKey()).length();
            chars+=m.getValue();
        }
        return sum/chars;
    }
}
