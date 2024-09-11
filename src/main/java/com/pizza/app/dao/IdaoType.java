package com.pizza.app.dao;

import com.pizza.app.bo.TypeProduit;

import java.util.List;

public interface IdaoType {
    List<TypeProduit> findAll();

    TypeProduit findById(Long id);
}
