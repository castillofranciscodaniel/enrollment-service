package com.org.enrollment.service.core.model;

import java.util.Collection;

public class SimplePage<E> {
    private Integer page;
    private Integer size;
    private Long totalPages;
    private Long totalElement;
    private Collection<E> content;

    public SimplePage() {
    }

    public SimplePage(Integer page, Integer size, Long totalPages, Long totalElement, Collection<E> content) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElement = totalElement;
        this.content = content;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(Long totalElement) {
        this.totalElement = totalElement;
    }

    public Collection<E> getContent() {
        return content;
    }

    public void setContent(Collection<E> content) {
        this.content = content;
    }
}
