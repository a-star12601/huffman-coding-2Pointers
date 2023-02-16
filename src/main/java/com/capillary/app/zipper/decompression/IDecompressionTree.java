package com.capillary.app.zipper.decompression;

import com.capillary.app.general.Node;

import java.io.IOException;
import java.util.Map;

public interface IDecompressionTree<T> {

    Map<T,Integer> getFrequencyMap(byte[] arr) throws IOException, ClassNotFoundException ;

    Node regenerateTree(Map<T,Integer> map);

    long getMapSize();
}
