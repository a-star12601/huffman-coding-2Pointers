package com.capillary.app.interfaces.decompression;

import com.capillary.app.general.Node;

import java.io.IOException;
import java.util.HashMap;

public interface IDecompressionTree {

    HashMap<?,Integer> getFrequencyMap(byte[] arr) throws IOException, ClassNotFoundException ;

    Node regenerateTree(HashMap<?,Integer> map);

    long getMapSize();
}
