package br.com.convoquei.backend._shared.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PagedRequest {

    @Min(0)
    private Integer page = 0;

    @Min(1) @Max(100)
    private Integer size = 10;

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public PageRequest toPageRequest() {
        int p = page == null ? 0 : page;
        int s = size == null ? 10 : size;
        return PageRequest.of(p, s);
    }

    public PageRequest toPageRequest(Sort sort) {
        int p = page == null ? 0 : page;
        int s = size == null ? 10 : size;
        return PageRequest.of(p, s, sort);
    }
}
