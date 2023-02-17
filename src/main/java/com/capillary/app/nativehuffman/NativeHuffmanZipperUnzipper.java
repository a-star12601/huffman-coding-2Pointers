package com.capillary.app.nativehuffman;

import com.capillary.app.general.*;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompression;
import com.capillary.app.nativehuffman.compression.NativeHuffmanCompressionTree;
import com.capillary.app.nativehuffman.decompression.NativeHuffmanDecompression;
import com.capillary.app.nativehuffman.decompression.NativeHuffmanDecompressionTree;
import com.capillary.app.zipper.compression.ICompression;
import com.capillary.app.zipper.compression.ICompressionTree;
import com.capillary.app.zipper.decompression.IDecompression;
import com.capillary.app.zipper.decompression.IDecompressionTree;
import com.capillary.app.zipper.IZipperUnzipper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NativeHuffmanZipperUnzipper implements IZipperUnzipper {
    private FileRead fr;
    private ICompressionTree cTree;
    private ICompression comp;
    private FileWrite fw;
    private IDecompressionTree dTree;
    private IDecompression decomp;


    public NativeHuffmanZipperUnzipper(){
        fr = new FileRead<Character>();
        cTree = new NativeHuffmanCompressionTree();
        comp = new NativeHuffmanCompression();
        fw = new FileWrite<Character>();
        dTree = new NativeHuffmanDecompressionTree();
        decomp = new NativeHuffmanDecompression();
    }

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
            System.out.println("Average LPC : "+average);

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
            Map<Character, Integer> map = crt.getMap();
            byte[] compressBytes = crt.getByteArray();

            Node tree=dTree.regenerateTree(map);

            byte[] exportBytes=decomp.getDecompressedBytes(compressBytes,tree,decomp.getCharCount(map));

            fw.writeDecomp(decompressedFile,false,exportBytes);
        } catch (IOException e) {
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
