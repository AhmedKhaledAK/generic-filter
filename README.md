# generic-filter
Generic filter and sorter. This is library that sorts and filters any given list of objects regardless of the type and given fields. 

## Usage 
> Go to https://search.maven.org/artifact/io.github.ahmedkhaledak/generic-filter/1.0.0/jar and use the maven or gradle dependency according to your project.

* For example, if your project is maven based, go to **pom.xml** and insert the following code:
```
<dependency>
  <groupId>io.github.ahmedkhaledak</groupId>
  <artifactId>generic-filter</artifactId>
  <version>1.0.0</version>
</dependency>
```

* To instantiate a GenericFilter object, use this code: 
```
GenericFilter genericFitler = GenericFilterFactory.getInstance();
```

You can then use this object to sort and filter lists. 

<hr>

## Models
At all times, you will deal with the following models.
#### SortData
This is used to specify the fieldName to sort the list by, and whether the sorting should be done in an ascending order.
```
SortData {
  String fieldName;
  Boolean isAsc;
}
```
#### FilterOperator
This is an enum which is used to specify your filter operator.
```
enum FilterOperator {
  EQUAL,
  NOT_EQUAL,
  GREATER_THAN,
  LESS_THAN,
  NOT_CONTAINS,
  CONTAINS
}
```
#### FilterPredicate
This is used to create the filter predicate - what fieldName you want to filter on and its value, and what operator to use.
```
FilterPredicate {
  String fieldName;
  FilterOperator filterOperator;
  String value;
}
```
<hr>

## Sorting
```
class Employee {
  int age;
  int salary;
  String name;
}
List<Employee> employees = getEmployees();

SortData sortData = new SortData();
sortData.setFieldName("age");
sortData.setAsc(true);
// sort list ascendingly by age
List<Employee> sortedEmployeesByAge = genericFilter.sortList(employees, sortData);

SortData sortData = new SortData();
sortData.setFieldName("name");
sortData.setAsc(false);
// sort list descendingly by name
List<Employee> sortedEmployeesByAge = genericFilter.sortList(employees, sortData);
```
<hr>

## Filtering
> Note that filtering only works for the following data types (at the moment): Integer, Long, Double, BigDecimal, Boolean, LocalDateTime, Date

```
class Employee {
  int age;
  int salary;
  String name;
}

List<Employee> employees = getEmployees();

List<FilterPredicate> filterPredicates = new ArrayList<>();
filterPredicates.add(new FilterPredicate("age", FilterOperator.NOT_EQUAL, "20"));
filterPredicates.add(new FilterPredicate("name", FilterOperator.CONTAINS, "Ahmed"));
// filter list and keep employees with age NOT EQUAL to 20 and name DOES NOT CONTAIN Ahmed
List<Employee> sortedEmployeesByAge = genericFilter.filterList(employees, filterPredicates);

```

