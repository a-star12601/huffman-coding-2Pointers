package com.capillary.app.interfaces.decompression;

import com.capillary.app.general.Node;

import java.util.HashMap;

public interface IDecoder {
    byte[] decodingLogic(byte[] arr, Node tree, long mapsize, long count);

    int getCount(HashMap<?,Integer> map);

}
