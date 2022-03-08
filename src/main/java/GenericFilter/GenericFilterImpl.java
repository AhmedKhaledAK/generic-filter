package GenericFilter;

import GenericFilter.models.FilterOperator;
import GenericFilter.models.FilterPredicate;
import GenericFilter.models.SortData;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class GenericFilterImpl implements GenericFilter {
    @Override
    public List<?> sortList(List<?> sourceList, SortData sortData) {
        if (sourceList == null || sourceList.isEmpty() || sortData == null) {
            return sourceList;
        }

        List<Field> objectFields = getAllFields(sourceList.get(0));
        Field sortField = objectFields.stream().filter(field -> field.getName().equals(sortData.getFieldName())).findFirst().orElse(null);
        if (sortField == null) {
            return sourceList;
        }
        sortField.setAccessible(true);

        sourceList.sort((object1, object2) -> {
            try {
                Comparable<Object> property1 = (Comparable<Object>) sortField.get(object1);
                Comparable<Object> property2 = (Comparable<Object>) sortField.get(object2);
                if (sortData.isAsc()) {
                    return property1 != null ? property1.compareTo(property2) : -1; // asc = Null values on top
                }
                return property2 != null ? property2.compareTo(property1) : 1; // desc = Null values on the bottom
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return 0;
        });
        return sourceList;
    }

    @Override
    public List<?> filterList(List<?> sourceList, List<FilterPredicate> filterPredicates) {
        if (sourceList == null || sourceList.isEmpty() || filterPredicates == null || filterPredicates.isEmpty()) {
            return sourceList;
        }

        List<Field> objectFields = getAllFields(sourceList.get(0));
        List<Object> filteredResults = new ArrayList<>();

        for (Object object : sourceList) {
            boolean isObjectMatchFilters = true;
            boolean isFieldFound = true;
            for (FilterPredicate filter : filterPredicates) {
                String filterFieldName = filter.getFieldName();
                String filterValue = filter.getValue();
                FilterOperator operator = filter.getFilterOperator();

                Field filterField = objectFields.stream().filter(f -> f.getName().equals(filterFieldName)).findFirst().orElse(null);
                if (filterField == null) {
                    isFieldFound = false;
                    break;
                }
                filterField.setAccessible(true);

                isObjectMatchFilters = isObjectMatchFilter(object, filterField, filterValue, operator);
                if (!isObjectMatchFilters) {
                    break;
                }
            }
            if (!isFieldFound) {
                break;
            }
            if (isObjectMatchFilters) {
                filteredResults.add(object);
            }
        }
        return filteredResults;
    }

    private Boolean isObjectMatchFilter(Object object, Field field, String searchValue, FilterOperator operator) {
        try {
            Class<?> type = field.getType();
            Object typedSearchValue = getTypedSearchValue(type, searchValue);
            Object objectValue = field.get(object);
            return isFieldValueMatchSearch(objectValue, typedSearchValue, operator);
        } catch (IllegalAccessException e) {
            // Should not happen, field belong to object and is accessible.
            e.printStackTrace();
        }
        return false;
    }

    private Boolean isFieldValueMatchSearch(Object value, Object searchValue, FilterOperator operator) {
        switch (operator) {
            case EQUAL:
                return searchValue.equals(value);
            case CONTAINS:
                if (!(value instanceof String) || !(searchValue instanceof String)) {
                    return false;
                }
                return ((String) value).contains((String) searchValue);
            case NOT_EQUAL:
                return !searchValue.equals(value);
            case LESS_THAN:
                Comparable<Object> comparableValue = (Comparable<Object>) value;
                Comparable<Object> comparableSearchValue = (Comparable<Object>) searchValue;
                return comparableValue != null && comparableValue.compareTo(comparableSearchValue) < 0;
            case GREATER_THAN:
                comparableValue = (Comparable<Object>) value;
                comparableSearchValue = (Comparable<Object>) searchValue;
                return comparableValue != null && comparableValue.compareTo(comparableSearchValue) > 0;
            case NOT_CONTAINS:
                if (!(value instanceof String) || !(searchValue instanceof String)) {
                    return false;
                }
                return !((String) value).contains((String) searchValue);
        }
        return true;
    }

    private Object getTypedSearchValue(Class<?> type, String searchValue) {
        Object newSearchValue = searchValue;
        if (type.isAssignableFrom(Integer.class)) {
            newSearchValue = Integer.valueOf(searchValue);
        } else if (type.isAssignableFrom(Double.class)) {
            newSearchValue = Double.valueOf(searchValue);
        } else if (type.isAssignableFrom(Long.class)) {
            newSearchValue = Long.valueOf(searchValue);
        } else if (type.isAssignableFrom(BigDecimal.class)) {
            newSearchValue = new BigDecimal(searchValue);
        } else if (type.isAssignableFrom(Boolean.class)) {
            newSearchValue = Boolean.valueOf(searchValue);
        } else if (type.isAssignableFrom(LocalDateTime.class)) {
            newSearchValue = LocalDateTime.parse(searchValue);
        } else if (type.isAssignableFrom(Date.class)) {
            newSearchValue = Date.from(Instant.parse(searchValue));
        }
        return newSearchValue;
    }

    private List<Field> getAllFields(Object obj) {
        List<Field> fields = new ArrayList<>();
        getAllFieldsRecursive(fields, obj.getClass());
        return fields;
    }

    private List<Field> getAllFieldsRecursive(List<Field> fields, Class<?> type) {
        Collections.addAll(fields, type.getDeclaredFields());

        if (type.getSuperclass() != null) {
            fields = getAllFieldsRecursive(fields, type.getSuperclass());
        }
        return fields;
    }
}
