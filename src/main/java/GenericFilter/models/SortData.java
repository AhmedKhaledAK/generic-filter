package GenericFilter.models;

public class SortData {
    private String fieldName;
    private Boolean isAsc;

    public SortData() {
    }

    public SortData(String fieldName, Boolean isAsc) {
        this.fieldName = fieldName;
        this.isAsc = isAsc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getAsc() {
        return isAsc;
    }

    public void setAsc(Boolean asc) {
        isAsc = asc;
    }
}
