package com.convert.cv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


class Temp {

    Temp(String in, int pos, String[] output, Factory f) {

        int pre = f.getDigits();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(f.getDivider());

        DecimalFormat num = new DecimalFormat();
        num.setMaximumFractionDigits(pre);
        num.setDecimalFormatSymbols(symbols);

        final BigDecimal F1 = new BigDecimal("32");
        final BigDecimal F2 = new BigDecimal("5");
        final BigDecimal F3 = new BigDecimal("9");
        final BigDecimal C = new BigDecimal("1.8");
        final BigDecimal K = new BigDecimal("273.15");

        BigDecimal god = new BigDecimal(in);
        switch(pos){
           case 1 :
             god = god.subtract(F1).multiply(F2).divide(F3, pre, RoundingMode.HALF_UP);
             //Фаренгейт
           break;

           case 2 :
             god = god.subtract(K);
             //Кельвин
           break;

           case 3 :
             god = god.multiply(F2).divide(F3, pre, RoundingMode.HALF_UP).subtract(K);
             //Ранкин
           break;

           case 4 :
             god = god.multiply(new BigDecimal("1.25"));
            //Реомюр
           break;
        }

        output[0] = num.format(god);
        output[1] = num.format(god.multiply(C).add(F1));
        output[2] = num.format(god.add(K));
        output[3] = num.format(god.add(K).multiply(C));
        output[4] = num.format(god.multiply(new BigDecimal("0.8")));


    }
}
