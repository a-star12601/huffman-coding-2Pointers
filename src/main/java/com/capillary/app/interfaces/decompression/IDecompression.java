package com.capillary.app.interfaces.decompression;

import com.capillary.app.general.Node;

import java.util.HashMap;

public interface IDecompression {
    byte[] getDecompressedBytes(byte[] arr, Node tree, long mapsize, long count);

    int getCharCount(HashMap<?,Integer> map);

}
