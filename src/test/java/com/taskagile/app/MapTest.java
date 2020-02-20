package com.taskagile.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapTest {

    private String getCountry(String key){
        System.out.println("@@@@@ : " + key);
        return key.equals("4") ? "CHINA" : "CANADA";
    }

    @Test
    public void map_function_computeIfAbsent() {

        List<String> keyList = new ArrayList<>();
        keyList.add("1");
        keyList.add("2");
        keyList.add("3");
        keyList.add("4");
        keyList.add("5");

        Map<String, String> map = new HashMap<>();
        map.put("1", "KOREA");
        map.put("2", "USA");
        map.put("3", "JAPAN");

        for(String s : keyList){
            map.computeIfAbsent( s, (key) -> getCountry(key) );
        }

        System.out.println("map = " + map);
    }

    @Test
    public void map_function_putIfAbsent(){

        List<String> keyList = new ArrayList<>();
        keyList.add("1");
        keyList.add("2");
        keyList.add("3");
        keyList.add("4");
        keyList.add("5");

        Map<String, String> map = new HashMap<>();
        map.put("1", "KOREA");
        map.put("2", "USA");
        map.put("3", "JAPAN");

        for(String s : keyList) {
            map.putIfAbsent(s, getCountry(s));
        }
        System.out.println("map = " + map);
    }
    


    String text = "KOREA KOREA USA USA USA CANADA JAPAN ";

    Map<String, Integer> wordMap = new HashMap<>();

    @BeforeEach
    public void setup2()
    {
        wordMap.put("KOREA", 0);
        wordMap.put("USA", 0);
        wordMap.put("CHINA", 0);
        wordMap.put("JAPAN", 0);
    }

    @Test
    public void map_function_computeIfPresent() {
        
        for(String word : text.split(" ")){
            wordMap.computeIfPresent(word, (String key, Integer value) ->  ++value);
        }

        System.out.println("wordMap = " + wordMap);
    }

    @Test
    public void map_function_compute() {

        assertThrows(NullPointerException.class, ()->{

            for(String word : text.split(" ")){
                wordMap.compute(word, (String key, Integer value) -> ++value);
            }

            System.out.println("wordMap = " + wordMap);
        });
    }


    @Test
    public void map_function_getOrDefault(){

        Map<String, String> map = new HashMap<>();
        map.put("1", "KOREA");
        map.put("2", "USA");

        System.out.println("map = " + map.getOrDefault("3", "FRANCE"));
        System.out.println("map = " + map.get("3"));

    }

}
