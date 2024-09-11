package com.pizza.app.bdd;

import com.pizza.app.bo.TypeProduit;

import java.util.List;

public interface ITypeManager {
    List<TypeProduit> getTypes();
    TypeProduit getType(Long id);

}
