package br.com.geancesar.eufood.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private static Util instance;

    private Util(){}

    public static Util getInstance() {
        if(instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public String formataData(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    public String formataMoeda(BigDecimal valor) {
        valor = valor.setScale(2, RoundingMode.HALF_EVEN);
        return "R$ " + String.valueOf(valor).replace(".", ",");
    }

    public String formataMoeda(double valor) {
        return formataMoeda(BigDecimal.valueOf(valor));
    }

}
