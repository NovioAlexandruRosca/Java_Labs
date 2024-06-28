package org.example;

import com.sun.source.tree.Tree;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

abstract class Main {

    public static List<int[]> fa(List<int[]> list) {
        int lungimeMax = 0;
        List<int[]> returnList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (lungimeMax < list.get(i).length) {
                lungimeMax = list.get(i).length;
            }
        }

        // SAU

//        int max = list.stream().map(lst -> lst.length).max(Integer::compareTo).orElse(0);

        for (int i = 0; i < list.size(); i++) {
            int[] newLine = new int[lungimeMax];
            for (int j = 0; j < list.get(i).length; j++) {
                newLine[j] = list.get(i)[j];
            }
            for (int j = list.get(i).length; j < lungimeMax; j++) {
                newLine[j] = 0;
            }
            returnList.add(newLine);
        }

        return returnList;
    }

    public static void lol(int[] list) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < list.length; i++) {
            List<Integer> list1 = new ArrayList<>();

            for (int j = 1; j <= list[i]; j++) {
                if (list[i] % j == 0) {
                    list1.add(j);
                }
            }

            map.put(list[i], list1);
        }

        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            Integer key = entry.getKey();
            List<Integer> value = entry.getValue();

            System.out.print(key + ",(");
            for (int i = 0; i < value.size(); i++) {
                System.out.print(value.get(i) + ",");
            }
            System.out.println(")");
        }

    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        int[][] matrix = {{0, 1, 2, 3, 4}, {5, 6, 7, 8, 9}};

//        List<int[]> returnList = new ArrayList<>();
//        int[] newLine1 = {1,2,3};
//        int[] newLine2 = {1};
//        int[] newLine3 = {10,20};
//
//        List<String> list = new HashSet<>();
//
//        returnList.add(newLine1);
//        returnList.add(newLine2);
//        returnList.add(newLine3);
//
//        List<int[]> lol = fa(returnList);
//        for(int i = 0 ; i < lol.size() ; i++){
//            for(int j = 0 ; j < lol.get(i).length ; j++){
//                System.out.print(lol.get(i)[j] + " ");
//            }
////        }
//
//        List<Pets> pets = new ArrayList<>();
//
//        Pets pet1 = new Pets("LOL2",1);
//        Pets pet2 = new Pets("LOL3",2);
//        Pets pet3 = new Pets("LOL1",3);
//
//        pets.add(pet1);
//        pets.add(pet2);
//        pets.add(pet3);
//
//        List<Pets> sortedPets = pets.stream().sorted(new Comparator<Pets>() {
//            @Override
//            public int compare(Pets o1, Pets o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        }).toList();
//
//        for(Pets pet : sortedPets){
//            System.out.println(pet);
//        }
//
//        int[] nuu = {2,6};
//
//        Main.lol(nuu);
//
//
//        Class clazz = Class.forName("lol");
//        Class[] signature = new Class[] {int.class, String.class};
//        Constructor constructor = clazz.getConstructor(signature);
//        Lol lol = (Lol) constructor.newInstance(1,"LOL");
//
//
//    }
//
//    Algorithm load(String clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//        Class claz = Class.forName(clazz);
//        return (Algorithm) claz.getConstructor().newInstance();
//    }

    }
}