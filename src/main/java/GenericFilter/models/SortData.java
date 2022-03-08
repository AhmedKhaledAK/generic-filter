package GenericFilter.models;

public class SortData {
    private String fieldName;
    private Boolean asc;

    public SortData() {
    }

    public SortData(String fieldName, Boolean asc) {
        this.fieldName = fieldName;
        this.asc = asc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean isAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }
}
