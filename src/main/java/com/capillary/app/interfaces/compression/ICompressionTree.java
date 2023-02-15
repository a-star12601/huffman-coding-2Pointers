package com.capillary.app.interfaces.compression;

import com.capillary.app.general.Node;

import java.util.Map;

public interface ICompressionTree {

    Map<?,Integer> getFrequencyMap(byte[] arr) ;

    Node generateTree(Map<?,Integer> map);

    Map<?,String> getHashTable(Node tree);
}
