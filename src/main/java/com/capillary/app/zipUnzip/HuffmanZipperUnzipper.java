package com.capillary.app.zipUnzip;

import com.capillary.app.decompression.TreeDecoder;
import com.capillary.app.general.FileOperations;
import com.capillary.app.general.Node;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

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
            HashMap<Character,Integer> map= (HashMap<Character, Integer>) object.dec.initialiseMap(compressBytes);
//            System.out.println("Creating Huffman Tree...");
            Node tree=object.dec.initialiseTree(map);
            //        decode.GenerateTreeMap();
            //        System.out.println("Decompressing...");
            TreeDecoder decoder=new TreeDecoder();
            byte[] exportBytes=decoder.decodingLogic(compressBytes,tree,object.dec.getMapSize(),decoder.getCount(map));
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
