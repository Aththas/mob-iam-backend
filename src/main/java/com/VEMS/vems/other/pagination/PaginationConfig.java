package com.VEMS.vems.other.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationConfig {
    public Pageable getPageable(int page, int size, String sortBy, boolean ascending) {
        Sort sort = Sort.by(sortBy);
        sort = ascending? sort.ascending() : sort.descending();
        return PageRequest.of(page, size, sort);
    }
}
