package com.capillary.app.hybridhuffman;

import com.capillary.app.general.Node;
import com.capillary.app.hybridhuffman.compression.HybridHuffmanCompressionTree;

import java.io.IOException;
import java.util.Map;

public class PercentageTask {
    HybridHuffmanZipperUnzipper zip=new HybridHuffmanZipperUnzipper();
    HybridHuffmanCompressionTree cTree=new HybridHuffmanCompressionTree();
    Map<String,Integer> tempMap;
    Node tree;
    Map<String,String> hash;
    long mapSize=-1, fileSize=-1;



    long totalSize=-1;
    void getVals(Map<String,Integer> mp, int i) throws IOException {
        tempMap = zip.generateDynamicMap(mp, i);
        tree = cTree.generateTree(tempMap);
        hash = cTree.getHashTable(tree);
        fileSize= zip.getFileSize(tempMap, hash);
        mapSize = zip.getMapSize(tempMap);
        totalSize = fileSize+mapSize;
    }
}
