package com.capillary.app.zipper;

import com.capillary.app.huffman.compression.HuffmanEncoding;
import com.capillary.app.interfaces.compression.IEncodeTree;
import com.capillary.app.interfaces.compression.IEncoder;
import com.capillary.app.huffman.compression.TreeEncoder;
import com.capillary.app.huffman.decompression.HuffmanDecoding;
import com.capillary.app.interfaces.decompression.IDecodeTree;
import com.capillary.app.interfaces.decompression.IDecoder;
import com.capillary.app.huffman.decompression.TreeDecoder;

public class PassableObject {
    String original;
    String compressed;
    IEncodeTree enc;
    IEncoder encoder;
    IDecodeTree dec;
    IDecoder decoder;


    public PassableObject(String originalFile, String compressedFile){
        original=originalFile;
        compressed=compressedFile;
        enc=new HuffmanEncoding();
        encoder=new TreeEncoder();
        dec=new HuffmanDecoding();
        decoder=new TreeDecoder();
    }
}
