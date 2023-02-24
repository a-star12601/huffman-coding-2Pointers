package com.capillary.app.hybridhuffman;

import java.util.Map;
import java.util.concurrent.Callable;

public class BSTask implements Callable<Integer> {
    Map<String,Integer> mp;
    int r;
    BSObject ob;

    public BSTask(Map<String,Integer> _mp, int _r){
        mp = _mp;
        r = _r;
        ob=new BSObject(mp);
    }
    int bestPercentage=-1;
    @Override
    public Integer call() throws Exception {

        Integer obj[]=new Integer[25];
        for(int i=0;i<25;i++){
            obj[i]=Integer.MAX_VALUE;
        }
        return findLM(obj,0,25);
    }

    private Integer findLM(Integer[] obj,int start,int end){
        int mid=start+(end-start)/2;
        int size=obj.length;

        if(obj[mid]==Integer.MAX_VALUE){
            ob.generateMap(mid+25*r);
            obj[mid]= Math.toIntExact(ob.totalSize);
        }
        if((mid+1)>=0 && (mid+1)<size && obj[mid+1]==Integer.MAX_VALUE){
            ob.generateMap(mid+1+25*r);
            obj[mid]= Math.toIntExact(ob.totalSize);
        }
        if((mid-1)>=0 && (mid-1)<size && obj[mid-1]==Integer.MAX_VALUE){
            ob.generateMap(mid-1+25*r);
            obj[mid]= Math.toIntExact(ob.totalSize);
        }
        if((mid==0 || obj[mid-1]>obj[mid]) &&
                (mid==size-1 || obj[mid+1]>obj[mid])) {
            bestPercentage=mid+25*r;
            return obj[mid];
        }
            /* check if left neighbor is less than mid element, if yes go left */
        else if(mid>0 && obj[mid]>obj[mid-1]){
            return findLM(obj, start, mid);
        }
        else {
            /*check if right neighbor is greater than mid element, if yes go right */
            return findLM(obj, mid+1, end);
        }
    }
}
