package com.capillary.app.zipper.compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The interface for performing Compression.
 *
 * @param <T> the Generic type
 */
public interface ICompression<T> {

    /**
     * Convert Bytelist to byte array.
     *
     * @param bytes the Byte list
     * @return equivalent byte array
     */
    default byte[] byteFromByteList(List<Byte> bytes){
        byte[] exportBytes=new byte[bytes.size()];
        int i=0;
        for(Byte b:bytes){
            exportBytes[i++]=b.byteValue();
        }
        return exportBytes;
    }


    /**
     * Returns compressed bytes using hashmap and input array.
     *
     * @param arr  the input byte array
     * @param hash the hashmap
     * @return the compressed bytes
     */
    List<Byte> getCompressedBytes(byte[] arr, Map<T,String> hash);
}
