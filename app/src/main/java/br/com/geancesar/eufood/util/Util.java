package br.com.geancesar.eufood.util;

import java.math.BigDecimal;

public class Util {

    private static Util instance;

    private Util(){}

    public static Util getInstance() {
        if(instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public String formataMoeda(BigDecimal valor) {
        return "R$ " + String.valueOf(valor).replace(".", ",");
    }

}
