package GenericFilter;

import GenericFilter.models.FilterPredicate;
import GenericFilter.models.SortData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
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
        return null;
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
