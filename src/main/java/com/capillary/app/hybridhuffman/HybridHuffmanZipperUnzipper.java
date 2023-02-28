package com.capillary.app.hybridhuffman;

import com.capillary.app.general.*;
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
import java.util.*;


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
    Map<String,Integer> mp;
    Map<String,Integer> mp2;
    Map<String,Integer> bestMap;

    public void generateDynamicMap(List<Map.Entry<String, Integer>> list){
        Node tree=cTree.generateTree(mp);
        Map<String,String> hash= cTree.getHashTable(tree);

        long mapsize=getMapSize(mp);
        long filesize=getFileSize(mp,hash);

        long bestSize=mapsize+filesize;

        int counter=0;
        int limit= (int) (list.size()*0.02);

        for(Map.Entry<String,Integer> word:list){
            mp.remove(word.getKey());
            for(char c : word.getKey().toCharArray()){
                mp.put(c+"", mp.getOrDefault(c+"", 0)+word.getValue());
            }
            if(++counter==limit){
                tree=cTree.generateTree(mp);
                hash= cTree.getHashTable(tree);

                mapsize=getMapSize(mp);
                filesize=getFileSize(mp,hash);
                counter=0;
                if(filesize+mapsize<bestSize){
                    bestSize=filesize+mapsize;
                    bestMap=mp;
                }
                else {
                    break;
                }
            }
        }

    }
/*
    public Map<String,Integer> getBestMap(Map<String,Integer> mp) throws IOException, InterruptedException {

        ExecutorService service = Executors.newCachedThreadPool();
        BSTask[] tasks = new BSTask[4];
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tasks[i] = new BSTask(mp, i);
            futureList.add(service.submit(tasks[i]));
        }

        long bestSize = Integer.MAX_VALUE;
        int bestPercentage=-1;

        for (int i = 0; i < 4; i++){
            try {
                int size = futureList.get(i).get();
                if(size<bestSize){
                    bestSize=size;
                    bestPercentage=tasks[i].bestPercentage;
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        service.shutdownNow();

//        Map<String ,Integer> bestMap=generateDynamicMap(mp,bestPercentage);

        System.out.println(bestPercentage+" "+bestSize);

        return bestMap;
    }


 */
private static List<Map.Entry<String, Integer> > getSortedList(Map<String ,Integer> map){
    List<Map.Entry<String, Integer> > list = new LinkedList<>(map.entrySet());
    Collections.sort(
            list,
            (a, b) -> {
                if(a.getValue()*a.getKey().length() != b.getValue()*b.getKey().length())
                    return a.getValue()*a.getKey().length() - b.getValue()*b.getKey().length();
                return a.getKey().length() - b.getKey().length();
            });
    return list;

}

    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }

    public void compress(String originalFile, String compressedFile){
        try {
            byte[] inputBytes = fr.readComp(originalFile);
            int mid=inputBytes.length/2;
            while(isLetterOrDigit((char) inputBytes[mid])){
                mid++;
            }

            byte half1[] = Arrays.copyOfRange(inputBytes, 0, mid);
            byte half2[] = Arrays.copyOfRange(inputBytes, mid, inputBytes.length);



            Thread t1=new Thread(() -> mp= cTree.getFrequencyMap(half1));
            t1.start();
            Thread t2=new Thread(() -> mp2=cTree.getFrequencyMap(half2));
            t2.start();
            t1.join();t2.join();
            for(Map.Entry<String,Integer> e:mp2.entrySet()){
                mp.put(e.getKey(),mp.getOrDefault(e.getKey(),0)+e.getValue());
            }


            bestMap=mp;
            List<Map.Entry<String, Integer>> list=getSortedList(mp);
            generateDynamicMap(list);
            //map = getBestMap(map);

            Node tree=cTree.generateTree(bestMap);
            Map<String,String> hash= cTree.getHashTable(tree);

            double average=WAvg(bestMap,hash);
            System.out.println("Average Bits per char : "+average);

            List<Byte> encodedList = comp.getCompressedBytes(inputBytes,hash);
            byte[] encodedBytes = comp.byteFromByteList(encodedList);

            GenerateHash gh = new GenerateHash();
            String fileHash = gh.getHash(inputBytes, "MD5");

            fw.writeComp(bestMap,encodedBytes,compressedFile, fileHash);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String decompress(String compressedFile, String decompressedFile) {
        try {
            ComplexReturnType crt = fr.readDecomp(compressedFile);
            Map<String, Integer> map = crt.getMap();
            byte[] compressBytes = crt.getByteArray();
            String hash = crt.getHash();

            Node tree=dTree.regenerateTree(map);

            byte[] exportBytes=decomp.getDecompressedBytes(compressBytes,tree,decomp.getCharCount(map));

            fw.writeDecomp(decompressedFile,false,exportBytes);
            return hash;
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

    private static long getFileSize(Map<String,Integer> map,Map<String,String> hash){
        long sum=0;
        for(Map.Entry<String,Integer> m: map.entrySet()){
            sum+=m.getValue()*hash.get(m.getKey()).length();
        }
        return (long) Math.ceil(sum/8);
    }

    private static int getMapSize(Map<String, Integer> mp) {

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(mp);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteOutputStream.toByteArray().length;
    }

}

