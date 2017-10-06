package com.tpg.brks.ms.expenses.service.converters;

import org.modelmapper.ModelMapper;

public abstract class DomainConverter {

    protected final ModelMapper modelMapper = new ModelMapper();
}
