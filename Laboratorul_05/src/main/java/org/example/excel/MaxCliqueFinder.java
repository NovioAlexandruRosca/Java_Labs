package org.example.excel;

import org.example.excel.Employee;
import org.jgrapht.alg.clique.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Uses the list of employess, creates a graph and find the maximum sets of people with a common capability
 */
public class MaxCliqueFinder {

    /**
     * A list of employeess
     */
    List<Employee> employeeList;

    MaxCliqueFinder(List<Employee> employeeList){
        this.employeeList = employeeList;
    }

    /**
     * Creates the graph and finds the the maximum set
     */
    public void graphMaker(){
        SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        for (Employee employee : employeeList) {
            graph.addVertex(employee.getId().toString());
        }

        Map<String, Set<String>> abilities = employeeList.stream()
                .collect(Collectors.toMap(
                        employee -> employee.getId().toString(),
                        employee -> employee.getCapabilities().stream()
                                .filter(capability -> !capability.isEmpty())
                                .collect(Collectors.toSet())
                ));

        for (Map.Entry<String, Set<String>> entry1 : abilities.entrySet()) {
            for (Map.Entry<String, Set<String>> entry2 : abilities.entrySet()) {
                if (!entry1.getKey().equals(entry2.getKey()) && !Collections.disjoint(entry1.getValue(), entry2.getValue())) {
                    graph.addEdge(entry1.getKey(), entry2.getKey());
                }
            }
        }

        BronKerboschCliqueFinder<String, DefaultEdge> cliqueSearcher = new BronKerboschCliqueFinder<>(graph);
        cliqueSearcher.forEach(System.out::println);
    }
}
