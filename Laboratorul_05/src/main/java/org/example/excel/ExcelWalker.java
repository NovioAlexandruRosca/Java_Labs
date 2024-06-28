package org.example.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The class populates a list of employees with their capabilites taken from an excel file.
 */
public class ExcelWalker {

    /**
     * The list of employees
     */
    private final List<Employee> employeeList;

    public ExcelWalker() {
        employeeList = new ArrayList<>();
        walkExcel();
    }

    /**
     * Walk the excel file(every row and collumn and get the information from it and saves it in a list)
     */
    private void walkExcel(){
        try {
            File file = new File("D:\\Fac\\java\\Rezolvari\\Laboratorul_05\\src\\main\\masterDir\\data.xls"); // Replace with your file path
            Workbook workbook = Workbook.getWorkbook(file);

            Sheet sheet = workbook.getSheet(0);

            for (int i = 0; i < sheet.getRows(); i++) {
                if(i != 0) {
                    Employee employee = new Employee();
                    for (int j = 0; j < sheet.getColumns(); j++) {

                        if (j != 0) {
                            Cell cell = sheet.getCell(j, i);
                            employee.addCapabilities(cell.getContents());
                        }
                    }
                    employeeList.add(employee);
                }
            }

            workbook.close();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getEmployeeList(){
        return employeeList;
    }
}