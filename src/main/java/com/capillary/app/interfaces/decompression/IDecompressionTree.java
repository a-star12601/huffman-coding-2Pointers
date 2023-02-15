package com.capillary.app.interfaces.decompression;

import com.capillary.app.general.Node;

import java.io.IOException;
import java.util.Map;

public interface IDecompressionTree {

    Map<?,Integer> getFrequencyMap(byte[] arr) throws IOException, ClassNotFoundException ;

    Node regenerateTree(Map<?,Integer> map);

    long getMapSize();
}
