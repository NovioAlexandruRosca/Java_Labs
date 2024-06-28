package org.example;

public class Pets implements Comparable<Pets>{

    String name;
    int age;

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Pets pet){
        return Integer.compare(this.getAge(), pet.getAge());
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Pets(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Pets{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
