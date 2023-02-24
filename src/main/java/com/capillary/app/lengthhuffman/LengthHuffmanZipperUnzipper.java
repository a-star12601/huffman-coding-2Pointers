package com.capillary.app.lengthhuffman;

import com.capillary.app.general.ComplexReturnType;
import com.capillary.app.general.FileRead;
import com.capillary.app.general.FileWrite;
import com.capillary.app.general.Node;
import com.capillary.app.lengthhuffman.compression.LengthHuffmanCompression;
import com.capillary.app.lengthhuffman.compression.LengthHuffmanCompressionTree;
import com.capillary.app.lengthhuffman.decompression.LengthHuffmanDecompression;
import com.capillary.app.lengthhuffman.decompression.LengthHuffmanDecompressionTree;
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
public class LengthHuffmanZipperUnzipper implements IZipperUnzipper {
    /**
     * The FileRead object.
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
    public LengthHuffmanZipperUnzipper(){
        fr = new FileRead<String>();
        cTree = new LengthHuffmanCompressionTree();
        comp = new LengthHuffmanCompression();
        fw = new FileWrite<String>();
        dTree = new LengthHuffmanDecompressionTree();
        decomp = new LengthHuffmanDecompression();
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
    public LengthHuffmanZipperUnzipper(FileRead fr, ICompressionTree cTree, ICompression comp,
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

            Map<String,Integer> map= cTree.getFrequencyMap(inputBytes);
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
