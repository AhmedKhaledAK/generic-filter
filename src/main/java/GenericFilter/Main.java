package GenericFilter;

import GenericFilter.models.Employee;
import GenericFilter.models.FilterOperator;
import GenericFilter.models.FilterPredicate;
import GenericFilter.models.SortData;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,35, 25000));
        employees.add(new Employee(2,20, 10000));
        employees.add(new Employee(3,25, 15000));

        GenericFilter genericFilter = GenericFilterFactory.getInstance();

        print(employees);
        print((List<Employee>)genericFilter.sortList(employees, new SortData("age", false)));

        List<FilterPredicate> filterPredicates = new ArrayList<>();
        filterPredicates.add(new FilterPredicate("age", FilterOperator.NOT_EQUAL, "20"));
        print((List<Employee>) genericFilter.filterList(employees, filterPredicates));
    }

    static void print(List<Employee> employees) {
        System.out.println("SIZE: " + employees.size());
        for (Employee employee : employees) {
            System.out.print(employee.number);
        }
        System.out.println();
    }
}
