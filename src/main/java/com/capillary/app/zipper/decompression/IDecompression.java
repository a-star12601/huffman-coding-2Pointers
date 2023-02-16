package com.capillary.app.zipper.decompression;

import com.capillary.app.general.Node;

import java.util.Map;

public interface IDecompression<T> {
    byte[] getDecompressedBytes(byte[] arr, Node tree, long mapsize, long count);

    int getCharCount(Map<T,Integer> map);

}
