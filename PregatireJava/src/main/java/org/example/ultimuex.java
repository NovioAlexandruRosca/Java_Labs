package org.example;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ultimuex {

    public boolean isArithmetic(int[] list){
        if(list.length == 0) return false;
        else if(list.length == 1) return true;
        else{
            int a = list[0];
            int b = list[1];
            int dif = b - a;

            for(int i = 2; i < list.length; i++){
                a = b;
                b = list[i];
                int dif1 = b - a;
                if(dif1 != dif) return false;
            }
        }

        return true;
    }

    public void Arithmetic(List<int[]> adj, int nr_fire){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nr_fire);
        for(int i = 0 ; i < adj.size() ; i++){
            int finalI = i;
            Runnable task = new Runnable(){
               @Override
               public void run(){
                   isArithmetic(adj.get(finalI));
               }
           };
           executor.execute(task);

        }
        executor.shutdown();

    }

    public static void main(String[] args) {

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
