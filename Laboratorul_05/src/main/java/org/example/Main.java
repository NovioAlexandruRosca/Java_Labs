package org.example;

import org.example.repository.DocumentRepo;
import org.example.shellLogic.Shell;

public class Main {
    public static void main(String[] args) {

        DocumentRepo repository = new DocumentRepo("D:\\Fac\\java\\Rezolvari\\Laboratorul_05\\src\\main\\masterDir");

        Shell shell = new Shell(repository);

//        ExcelWalker excel = new ExcelWalker();
//        MaxCliqueFinder maxCliqueFinder = new MaxCliqueFinder(excel.getEmployeeList());
//        maxCliqueFinder.graphMaker();

    }
}
