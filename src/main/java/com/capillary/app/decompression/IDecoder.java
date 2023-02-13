package com.capillary.app.decompression;

import com.capillary.app.general.Node;

import java.util.HashMap;
import java.util.List;

public interface IDecoder {
    byte[] decodingLogic(byte[] arr, Node tree, long mapsize, long count);

    int getCount(HashMap<?,Integer> map);

}
