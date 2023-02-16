package com.capillary.app.zipper.compression;

import com.capillary.app.general.Node;

import java.util.Map;

public interface ICompressionTree<T> {

    Map<T,Integer> getFrequencyMap(byte[] arr) ;

    Node generateTree(Map<T,Integer> map);

    Map<T,String> getHashTable(Node tree);
}
