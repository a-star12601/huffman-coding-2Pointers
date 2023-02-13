package com.capillary.app.zipUnzip;

import com.capillary.app.compression.HuffmanEncoding;
import com.capillary.app.compression.IEncodeTree;
import com.capillary.app.compression.IEncoder;
import com.capillary.app.compression.TreeEncoder;
import com.capillary.app.decompression.HuffmanDecoding;
import com.capillary.app.decompression.IDecodeTree;
import com.capillary.app.decompression.IDecoder;
import com.capillary.app.decompression.TreeDecoder;

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
