package GenericFilter.models;

public class FilterPredicate {
    private String fieldName;
    private FilterOperator filterOperator;
    private String value;

    public FilterPredicate() {
    }

    public FilterPredicate(String fieldName, FilterOperator filterOperator, String value) {
        this.fieldName = fieldName;
        this.filterOperator = filterOperator;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FilterOperator getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(FilterOperator filterOperator) {
        this.filterOperator = filterOperator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
