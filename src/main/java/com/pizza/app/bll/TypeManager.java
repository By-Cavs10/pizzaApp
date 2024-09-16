package com.pizza.app.bll;

import com.pizza.app.bo.TypeProduit;
import com.pizza.app.dao.IdaoType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeManager implements ITypeManager {

    private IdaoType typeDAO;
    public TypeManager(IdaoType typeDAO) {
        this.typeDAO = typeDAO;

    }
    @Override
    public List<TypeProduit> getTypes() {



        return typeDAO.findAll();
    }

    @Override
    public TypeProduit getType(Long id) {
        return typeDAO.findById(id);
    }


}
