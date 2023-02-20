package com.capillary.app.scaledhuffman;

import com.capillary.app.general.*;
import com.capillary.app.scaledhuffman.compression.ScaledHuffmanCompression;
import com.capillary.app.scaledhuffman.compression.ScaledHuffmanCompressionTree;
import com.capillary.app.scaledhuffman.decompression.ScaledHuffmanDecompression;
import com.capillary.app.scaledhuffman.decompression.ScaledHuffmanDecompressionTree;
import com.capillary.app.zipper.compression.ICompression;
import com.capillary.app.zipper.compression.ICompressionTree;
import com.capillary.app.zipper.decompression.IDecompression;
import com.capillary.app.zipper.decompression.IDecompressionTree;
import com.capillary.app.zipper.IZipperUnzipper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ScaledHuffmanZipperUnzipper implements IZipperUnzipper {
    FileRead fr;
    ICompressionTree cTree;
    ICompression comp;
    FileWrite fw;
    IDecompressionTree dTree;
    IDecompression decomp;


    public ScaledHuffmanZipperUnzipper(){
        fr = new FileRead<String>();
        cTree = new ScaledHuffmanCompressionTree();
        comp = new ScaledHuffmanCompression();
        fw = new FileWrite<String>();
        dTree = new ScaledHuffmanDecompressionTree();
        decomp = new ScaledHuffmanDecompression();
    }

    public ScaledHuffmanZipperUnzipper(FileRead fr, ICompressionTree cTree, ICompression comp,
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
            Map<String, Integer> map = crt.getMap();
            byte[] compressBytes = crt.getByteArray();



            Node tree=dTree.regenerateTree(map);

            byte[] exportBytes=decomp.getDecompressedBytes(compressBytes,tree,decomp.getCharCount(map));

            fw.writeDecomp(decompressedFile,false,exportBytes);
        } catch (IOException e) {
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
