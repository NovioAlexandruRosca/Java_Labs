package org.example.tests;

import org.example.annotations.Test;

@Test
public class MyTestClass {

    public void exampleMethod() {
        System.out.println("This is an example method.");
    }

    public static void yes(){
        System.out.println("Da");
    }

    @Test
    public static void testMethod() {
        System.out.println("This is a test method.");
    }

    @Test
    public static void anotherTestMethod() {
        System.out.println("This is another test method.");
    }

    @Test
    public static void boomTestMethod() {
        throw new RuntimeException("This is a boom test method.");
    }

    @Test
    public void homeworkTestMethod(int arg1, String arg2, int arg3) {
        System.out.println(arg1 + " " + arg2 + " " + arg3);
    }

    public static void main(String[] args) {
    }
}
