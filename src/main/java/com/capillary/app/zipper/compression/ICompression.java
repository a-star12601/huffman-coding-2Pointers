package com.capillary.app.zipper.compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The interface for a root.general Encoder.
 */
public interface ICompression<T> {

    default byte[] byteFromByteList(List<Byte> bytes){
        byte[] exportBytes=new byte[bytes.size()];
        int i=0;
        for(Byte b:bytes){
            exportBytes[i++]=b.byteValue();
        }
        return exportBytes;
    }


    /**
     * Method to Encode text.
     *
     * @param arr  the arr
     * @param hash the hash
     * @return the list
     * @throws FileNotFoundException the file not found exception
     */
    List<Byte> getCompressedBytes(byte[] arr, Map<T,String> hash);
}
