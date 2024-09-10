package com.pizza.app.ihm;

public class AuthFlashMessage {
    public static final int TYPE_FLASH_SUCCESS = 0;
    public static final int TYPE_FLASH_ERROR = 1;

    public int type;
    public String message;

    public AuthFlashMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Fonction qui sert uniquement pour le front
     * Permet de selectionner quelle classe CSS il faut utiliser
     * selon le type du message (success ? error ? etc)
     * @return
     */
    public String getTypeCssClass(){
        if (type == TYPE_FLASH_SUCCESS){
            return "uk-alert-success";
        }
        if (type == TYPE_FLASH_ERROR){
            return "uk-alert-danger";
        }
        return "uk-alert-primary";
    }
}
