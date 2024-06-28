package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandeling {


    public static void main(String[] args) {

        Map<String,List<String>> map = new HashMap<>();

        map.put("1", new ArrayList<>());
        map.put("2", new ArrayList<>());
        map.put("3", new ArrayList<>());

        for(Map.Entry<String,List<String>> entry : map.entrySet()){
            String key = entry.getKey();
            List<String> value = entry.getValue();

            value.add("1");

            List<String>value1 = entry.getValue();
            System.out.println(value1);
        }


//        FileReader fis = new FileReader("ex.txt" / "ex.dat" / "ex.ser");
//        FileWriter fis = new FileWriter("ex.txt" / "ex.dat" / "ex.ser");
//        FileOutputStream fis = new FileOutputStream("ex.txt" / "ex.dat" / "ex.ser");
//        FileInputStream fis = new FileInputStream("ex.txt" / "ex.dat" / "ex.ser");
//
//        //////////////////////
//
//        DataOutputStream = new DataOutputStream(fis);
//        ObjectOutputStream = new ObjectOutputStream(fis);
//        BufferedOutputStream = new BufferedOutputStream(fis);
//
//        try(Reader reader = new Reader(System.in) {
//        }){
//
//        }
    }
}
