package org.example;


public class Main {
    public static void main(String[] args) {

        Person person1 = new Person("Rosca Alexandru", 1);
        Person person2 = new Person("Popa Andrei", 2);

        Document document1 = new Document("Cv", "pdf");
        Document document2 = new Document("C4_Diagram", "jpg");
        Document document3 = new Document("Project List", "xlsx");

        DocumentRepo repository = new DocumentRepo("D:\\Fac\\java\\Rezolvari\\Laboratorul_05\\src\\main\\masterDir");
        repository.displayRepo();


    }
}
