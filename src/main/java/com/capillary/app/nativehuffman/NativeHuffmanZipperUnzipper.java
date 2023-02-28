package com.capillary.app.nativehuffman;

import com.capillary.app.general.*;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompression;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompressionTree;
import com.capillary.app.nativehuffman.decompression.NativeHuffmanDecompression;
import com.capillary.app.nativehuffman.decompression.NativeHuffmanDecompressionTree;
import com.capillary.app.zipper.IZipperUnzipper;
import com.capillary.app.zipper.compression.ICompression;
import com.capillary.app.zipper.compression.ICompressionTree;
import com.capillary.app.zipper.decompression.IDecompression;
import com.capillary.app.zipper.decompression.IDecompressionTree;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Zipper Class to combine encoding and decoding.
 */
public class NativeHuffmanZipperUnzipper implements IZipperUnzipper {
    private FileRead fr;
    private ICompressionTree cTree;
    private ICompression comp;
    private FileWrite fw;
    private IDecompressionTree dTree;
    private IDecompression decomp;


    /**
     * Constructor for zipper initialisation.
     */
    public NativeHuffmanZipperUnzipper(){
        fr = new FileRead<Character>();
        cTree = new NativeHuffmanCompressionTree();
        comp = new NativeHuffmanCompression();
        fw = new FileWrite<Character>();
        dTree = new NativeHuffmanDecompressionTree();
        decomp = new NativeHuffmanDecompression();
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
    public NativeHuffmanZipperUnzipper(FileRead fr, ICompressionTree cTree, ICompression comp,
                                       FileWrite fw, IDecompressionTree dTree, IDecompression decomp){
        this.fr = fr;
        this.cTree = cTree;
        this.comp = comp;
        this.fw = fw;
        this.dTree = dTree;
        this.decomp = decomp;
    }

    public void compress(String originalFile, String compressedFile){
        try {
            byte[] inputBytes = fr.readComp(originalFile);

            Map<Character,Integer> map= cTree.getFrequencyMap(inputBytes);
            Node tree=cTree.generateTree(map);
            Map<Character,String> hash= cTree.getHashTable(tree);

            double average=WAvg(map,hash);
            System.out.println("Average Bits per char : "+average);

            List<Byte> encodedList = comp.getCompressedBytes(inputBytes,hash);
            byte[] encodedBytes = comp.byteFromByteList(encodedList);

            GenerateHash gh = new GenerateHash();
            String fileHash = gh.getHash(inputBytes, "MD5");

            fw.writeComp(map,encodedBytes,compressedFile, fileHash);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String decompress(String compressedFile, String decompressedFile) {
        try {
            ComplexReturnType crt = fr.readDecomp(compressedFile);
            Map<Character, Integer> map = crt.getMap();
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
