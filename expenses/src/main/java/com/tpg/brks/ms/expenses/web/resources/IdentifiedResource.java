package com.tpg.brks.ms.expenses.web.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
public abstract class IdentifiedResource<T> extends Resource<T> {
    private Long resourceId;

    protected IdentifiedResource(T content, Link... links) {
        super(content, links);
    }
}
