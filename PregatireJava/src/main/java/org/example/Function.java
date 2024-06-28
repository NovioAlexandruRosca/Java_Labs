package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Function{

    Map<String, byte[][]> map = new HashMap<>();

    public Function(Map<String, byte[][]> map){
        this.map = map;
    }

    public List<String> returnKey() throws Exception {

        List<String> keys = new ArrayList<>();

        for(Map.Entry<String, byte[][]> entry : map.entrySet()){
            String key = entry.getKey();
            byte[][] value = entry.getValue();

            int sum = 0;

            if(value.length == 0){throw new Exception("Broken");}
            if(value.length != value[0].length){throw new Exception("Broken");}

            for(int i = 0 ; i < value.length ; i++){
                for(int j = 0 ; j < value[i].length ; j++){
                    if(i == j)
                        sum += value[i][j];
                }
            }
            if(sum == 0)
                keys.add(key);
        }

        return keys;
    }

}