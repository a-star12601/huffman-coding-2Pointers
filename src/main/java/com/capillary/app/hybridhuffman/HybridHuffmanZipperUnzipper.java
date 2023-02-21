package com.capillary.app.hybridhuffman;

import com.capillary.app.general.ComplexReturnType;
import com.capillary.app.general.FileRead;
import com.capillary.app.general.FileWrite;
import com.capillary.app.general.Node;
import com.capillary.app.hybridhuffman.compression.HybridHuffmanCompression;
import com.capillary.app.hybridhuffman.compression.HybridHuffmanCompressionTree;
import com.capillary.app.hybridhuffman.decompression.HybridHuffmanDecompression;
import com.capillary.app.hybridhuffman.decompression.HybridHuffmanDecompressionTree;
import com.capillary.app.zipper.IZipperUnzipper;
import com.capillary.app.zipper.compression.ICompression;
import com.capillary.app.zipper.compression.ICompressionTree;
import com.capillary.app.zipper.decompression.IDecompression;
import com.capillary.app.zipper.decompression.IDecompressionTree;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Zipper Class to combine encoding and decoding.
 */
public class HybridHuffmanZipperUnzipper implements IZipperUnzipper {
    /**
     * The FileRead object
     */
    FileRead fr;
    /**
     * The CompressionTree object.
     */
    ICompressionTree cTree;
    /**
     * The Compression object.
     */
    ICompression comp;
    /**
     * The FileWrite object.
     */
    FileWrite fw;
    /**
     * The DecompressionTree object.
     */
    IDecompressionTree dTree;
    /**
     * The Decompression object.
     */
    IDecompression decomp;


    /**
     * Constructor for zipper initialisation.
     */
    public HybridHuffmanZipperUnzipper(){
        fr = new FileRead<String>();
        cTree = new HybridHuffmanCompressionTree();
        comp = new HybridHuffmanCompression();
        fw = new FileWrite<String>();
        dTree = new HybridHuffmanDecompressionTree();
        decomp = new HybridHuffmanDecompression();
    }

    /**
     * Constructor for zipper testing.
     *
     * @param fr     the fileread object
     * @param cTree  the compressionTree object
     * @param comp   the compression object
     * @param fw     the filewrite object
     * @param dTree  the decompressionTree object
     * @param decomp the decompression object
     */
    public HybridHuffmanZipperUnzipper(FileRead fr, ICompressionTree cTree, ICompression comp,
                                       FileWrite fw, IDecompressionTree dTree, IDecompression decomp){
        this.fr = fr;
        this.cTree = cTree;
        this.comp = comp;
        this.fw = fw;
        this.dTree = dTree;
        this.decomp = decomp;
    }

    private long getFileSize(Map<String,Integer> map,Map<String,String> hash){
        long sum=0;
        for(Map.Entry<String,Integer> m: map.entrySet()){
            sum+=m.getValue()*hash.get(m.getKey()).length();
        }
        return (long) Math.ceil(sum/8);
    }

    private int getMapSize(Map<String, Integer> mp) throws IOException {

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

        objectOutputStream.writeObject(mp);
        objectOutputStream.flush();
        objectOutputStream.close();

        return byteOutputStream.toByteArray().length;
    }

    private Map<String, Integer> generateDynamicMap(Map<String, Integer> sortedMap, int percentage){
        Map<String, Integer> mp = new LinkedHashMap<>();

        int limit = (int) Math.ceil(sortedMap.size() *  ((double) percentage/100));
        int i=0;
        for (Map.Entry<String, Integer> m : sortedMap.entrySet()){
            if(i<limit){
                mp.put(m.getKey(), m.getValue());
                i++;
            }else{
                String k = m.getKey();
                if(k.length() > 1){
                    for(char c : k.toCharArray()){
                        mp.put(c+"", mp.getOrDefault(c+"", 0)+m.getValue());
                    }
                }else{
                    mp.put(k, mp.getOrDefault(k, 0)+m.getValue());
                }
            }
        }
//        System.out.println(mp.size());
        return mp;
    }

    private Map<String,Integer> getBestMap(Map<String,Integer> mp) throws IOException {
        Map<String,Integer> tempMap;
        Node tree;
        Map<String,String> hash;
        long mapSize=-1, fileSize=-1;

        long bestSize = Integer.MAX_VALUE;
        Map<String,Integer> bestMap = new HashMap<>();

        for(int i=0; i<=100; i=i+1){
            tempMap = generateDynamicMap(mp, i);
            tree = cTree.generateTree(tempMap);
            hash = cTree.getHashTable(tree);

            fileSize= getFileSize(tempMap, hash);
            mapSize = getMapSize(tempMap);

            if(fileSize + mapSize < bestSize){
                bestSize = fileSize + mapSize;
                bestMap = tempMap;

//                double average=WAvg(tempMap,hash);
//
//                System.out.print("Best Percentage : "+i+" %");
//                System.out.print("\t\tBest Size : "+ bestSize);
//                System.out.print("\t\tHeader Ratio : "+ ((double)mapSize/bestSize)*100);
//                System.out.println("\t\tBits per char : "+ average);
            }

            double average=WAvg(tempMap,hash);

            System.out.print("Best Percentage : "+i+" %");
            System.out.print("\t\tBest Size : "+ fileSize + mapSize);
            System.out.print("\t\tHeader Ratio : "+ ((double)mapSize/(fileSize + mapSize))*100);
            System.out.println("\t\tBits per char : "+ average);
        }



//        System.out.println("Best Percentage : "+bestPercentage+" %");
        return bestMap;
    }

    public void compress(String originalFile, String compressedFile){
        try {
            byte[] inputBytes = fr.readComp(originalFile);

            Map<String,Integer> map= cTree.getFrequencyMap(inputBytes);

            map = getBestMap(map);

            Node tree=cTree.generateTree(map);
            Map<String,String> hash= cTree.getHashTable(tree);

            double average=WAvg(map,hash);
            System.out.println("Average Bits per char : "+average);

            List<Byte> encodedList = comp.getCompressedBytes(inputBytes,hash);
            byte[] encodedBytes = comp.byteFromByteList(encodedList);

            fw.writeComp(map,encodedBytes,compressedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decompress(String compressedFile, String decompressedFile) {
        try {
            ComplexReturnType crt = fr.readDecomp(compressedFile);
            Map<String, Integer> map = crt.getMap();
            byte[] compressBytes = crt.getByteArray();



            Node tree=dTree.regenerateTree(map);

            byte[] exportBytes=decomp.getDecompressedBytes(compressBytes,tree,decomp.getCharCount(map));

            fw.writeDecomp(decompressedFile,false,exportBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to return Symbols/Character.
     *
     * @param map  the frequencymap
     * @param hash the hashmap
     * @return symbols/character
     */
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

