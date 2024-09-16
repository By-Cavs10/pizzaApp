package com.pizza.app.ihm.converter;

import com.pizza.app.bll.BasketManagerImpl;
import com.pizza.app.bo.EtatCommande;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEtatCommandeConverter   implements Converter<String, EtatCommande> {

    private BasketManagerImpl basketManager;

    public StringToEtatCommandeConverter(BasketManagerImpl basketManager) {
        this.basketManager = basketManager;
    }

    @Override
    public EtatCommande convert(String id) {

        return basketManager.getEtatCommande(Long.parseLong(id));
    }



}

