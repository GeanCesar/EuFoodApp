package br.com.geancesar.eufood.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        valor = valor.setScale(2, RoundingMode.HALF_EVEN);
        return "R$ " + String.valueOf(valor).replace(".", ",");
    }

    public String formataMoeda(double valor) {
        return formataMoeda(BigDecimal.valueOf(valor));
    }

}
