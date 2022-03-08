package GenericFilter;

import GenericFilter.models.FilterPredicate;
import GenericFilter.models.SortData;

import java.util.List;

public interface GenericFilter {
    List<?> sortList(List<?> sourceList, SortData sortData);
    List<?> filterList(List<?> sourceList, List<FilterPredicate> filterPredicates);
}
