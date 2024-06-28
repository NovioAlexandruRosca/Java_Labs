package org.example;

@ExpectedTime(time = 123f)
public class Sprite implements  Runnable{
    @Override
    public void run() {
        System.out.println("LOL");
    }
}
