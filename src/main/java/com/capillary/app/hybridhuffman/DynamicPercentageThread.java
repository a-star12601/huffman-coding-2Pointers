package com.capillary.app.hybridhuffman;

import com.capillary.app.general.Node;
import com.capillary.app.hybridhuffman.compression.HybridHuffmanCompressionTree;

import java.io.IOException;
import java.util.Map;

public class DynamicPercentageThread implements Runnable{
    int x,y;
    Map<String ,Integer> map;
    DynamicPercentageThread(int a, int b, Map<String ,Integer> mp){
        x=a;
        y=b;
        map=mp;
    }
    int bestPercent=-1;
    long bestSize=Integer.MAX_VALUE;
    Map<String,Integer> bestMap;
    public void run(){
        for(int i=x;i<y;i++) {
            try {
                getTempVals(map,i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        System.out.println(bestPercentage);
    }
    HybridHuffmanZipperUnzipper zip=new HybridHuffmanZipperUnzipper();
    HybridHuffmanCompressionTree cTree=new HybridHuffmanCompressionTree();
    Map<String,Integer> sortedMap;
    Map<String,Integer> tempMap;
    Node tree;
    Map<String,String> hash;
    long mapSize=-1, fileSize=-1;

    void getTempVals(Map<String,Integer> mp,int i) throws IOException {
        tempMap = zip.generateDynamicMap(mp, i);
        tree = cTree.generateTree(tempMap);
        hash = cTree.getHashTable(tree);
        //System.out.println(i);
        fileSize= zip.getFileSize(tempMap, hash);
        mapSize = zip.getMapSize(tempMap);

        if(fileSize + mapSize < bestSize){
            bestSize = fileSize + mapSize;
            bestMap = tempMap;
            bestPercent=i;
//                double average=WAvg(tempMap,hash);
//
//                System.out.print("Best Percentage : "+i+" %");
//                System.out.print("\t\tBest Size : "+ bestSize);
//                System.out.print("\t\tHeader Ratio : "+ ((double)mapSize/bestSize)*100);
//                System.out.println("\t\tBits per char : "+ average);
        }
    }

}
