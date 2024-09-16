package com.pizza.app.ihm.converter;

import com.pizza.app.bll.ITypeManager;
import com.pizza.app.bo.TypeProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTypeConverter implements Converter<String, TypeProduit> {
    @Autowired
    private ITypeManager typeManager;

    @Override
    public TypeProduit convert(String idTypes) {

        return typeManager.getType(Long.parseLong(idTypes));
    }
}