package com.tpg.brks.ms.expenses.web.resources;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public abstract class DomainResourceAssembler<T, U extends ResourceSupport> extends ResourceAssemblerSupport<T, U> {
    protected final ModelMapper modelMapper;

    protected DomainResourceAssembler(Class<T> tClass, Class<U> uClass) {
        super(tClass, uClass);

        this.modelMapper = new ModelMapper();
    }
}
