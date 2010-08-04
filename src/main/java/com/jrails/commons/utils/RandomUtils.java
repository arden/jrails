/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jrails.commons.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author tujiao
 */
public class RandomUtils {
     /**
     * 对指定的列表随机排序
     * @param records
     * @return
     */
    public static List random(List records) {
        List targetRecords = new ArrayList();        
        int size = records.size();
        Random random = new Random();        
        Set<Integer> randomSet = new LinkedHashSet<Integer>();      
        
        while(randomSet.size() < size) {
            int num = random.nextInt(size);
            randomSet.add(num);            
        }

        for (int i : randomSet) {
            targetRecords.add(records.get(i));
        }
        return targetRecords;
    }
    
    public static void main(String...args) {
        List<String> records = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            records.add("曹江华：" + i);    
        }

        System.out.println(RandomUtils.random(records));
        System.out.println(RandomUtils.random(records));
        System.out.println(RandomUtils.random(records));
        System.out.println(RandomUtils.random(records));        
    }
}
