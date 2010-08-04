package com.jrails.commons.utils;

import java.util.Set;

import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-3-28
 * Time: 10:49:07
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtils {

    public static String returnIdJson(List list,String [] array){

         if(list==null){
            return "{}";
         }

         String arrayStr = "";
         if(array != null && array.length > 0 ){
             for(int i=0;i<array.length;i++){
                String ar = array[i];
                arrayStr += ar + "," ;
            }
         }

        String str = "";
        for(int i=0;i<list.size();i++){
            String s = "";
            Map map = (Map) list.get(i);
            Set keys = map.keySet();
            Iterator iterator = keys.iterator();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if( arrayStr.equals("") || arrayStr.indexOf(key + ",") == -1 ){
                    s += s == "" ? (key + ":\"" + (String)map.get(key) + "\"") : ( "," + key + ":\"" + (String) map.get(key) + "\"");
                }
            }
            s =  map.get("id") + ":{" + s + "}";
            str += str == "" ? s : "," + s;
        }
        return  "{" + str + "}";
    }

    public static String returnIndexJson(List list,String [] array){

         if(list==null){
            return "{}";
         }

         String arrayStr = "";
         if(array != null && array.length > 0 ){
             for(int i=0;i<array.length;i++){
                String ar = array[i];
                arrayStr += ar + "," ;
            }
         }

        String str = "";
        for(int i=0;i<list.size();i++){
            String s = "";
            Map map = (Map) list.get(i);
            Set keys = map.keySet();
            Iterator iterator = keys.iterator();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if( arrayStr.equals("") || arrayStr.indexOf(key + ",") == -1 ){
                    s += s == "" ? (key + ":\"" + (String)map.get(key) + "\"") : ( "," + key + ":\"" + (String) map.get(key) + "\"");
                }
            }
            s =  i + ":{" + s + "}";
            str += str == "" ? s : "," + s;
        }
        return  "{" + str + "}";
    }

     public static String returnJson(List list,String [] array){

         if(list==null){
            return "[]";             
         }
         
         String arrayStr = "";            
         if(array != null && array.length > 0 ){
             for(int i=0;i<array.length;i++){
                String ar = array[i];
                arrayStr += ar + "," ;
            }
         }
         
        String str = "";
        for(int i=0;i<list.size();i++){
            String s = "";
            Map map = (Map) list.get(i);
            Set keys = map.keySet();
            Iterator iterator = keys.iterator();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if( arrayStr.equals("") || arrayStr.indexOf(key + ",") == -1 ){
                    s += s == "" ? (key + ":\"" + (String)map.get(key) + "\"") : ( "," + key + ":\"" + (String) map.get(key) + "\"");
                }
            }
            s =  "{" + s + "}";
            str += str == "" ? s : "," + s;
        }
        return  "[" + str + "]";
    }

}
