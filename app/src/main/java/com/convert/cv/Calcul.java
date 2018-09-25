package com.convert.cv;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by ADEPT on 09.05.2018.
 */

//optimize!!!
class Calcul {

    private String[] val;

    Calcul(String in, int id, int pos, String[] output, Factory f) {

     BigDecimal input = new BigDecimal(in);


     if(input.compareTo(BigDecimal.ZERO) == 0){
         for(int i = 0; i < output.length; i++) output[i] = "0";
         return;
     }

        switch (id) {

            case 0:
                val = new String[]{"1e-9", "1e-3", "1e-2", "0.1", "1", "1e3",
                        "1.5e11", "9.46e15", "3.09e16",
                        "0.0254", "0.1016", "0.201168", "0.3048", "0.9144", "5.0292", "20.1168", "201.17", "1609.34", "5555.5"};
            break;

            case 1 :
                val = new String[]{"1e-18", "1e-6", "1e-4", "0.01", "1", "100", "1e4", "1e6", "4046.87"};
            break;

            case 2 :
                val = new String[]{"1e-9", "1e-6", "1e-3", "1", "1e9", "5.506e-4", "9.464e-4", "4.405e-3", "0.159", "4.929e-6", "1.479e-5", "2.366e-4"};
            break;

            case 3 :
                val = new String[]{"1", "3.6", "3600", "1e-3", "60", "0.06",  "1.852", "1191.6", "1079252848.8"};
            break;

            case 4 :
                val = new String[]{"1e-6", "1e-3", "1", "100", "1e3", "2e-4", "6.5e-5", "1.772e-3", "0.02835",
                        "0.4536", "6.35"};
            break;

            case 5 :
                val = new String[]{"1", "1e3", "1e6", "4.187", "4187", "1", "3.6e6"};
            break;

            case 6 :
                val = new String[]{"1e-9", "1e-3", "1", "60", "3600", "86400", "604800", "2628000", "31536000", "31622400", "3.1536e9"};
            break;

            case 7 :
                val = new String[]{"8", "8192", "8388608", "8589934592", "8796093022208", "9007199254740992", "9223372036854775808",
                                   "1", "1024", "1048576", "1073741824", "1099511627776", "1125899906842624", "1152921504606846976"};
            break;
        }

        int pre = f.getDigits();


        MathContext mc = new MathContext(pre + 1, RoundingMode.HALF_UP);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(f.getDivider());

        DecimalFormat num = new DecimalFormat();
        num.setMaximumFractionDigits(pre);
        num.setDecimalFormatSymbols(symbols);

        DecimalFormat exp = new DecimalFormat("0.#E0");
        exp.setDecimalFormatSymbols(symbols);


        BigDecimal god = input.multiply(new BigDecimal(val[pos]));



        for (int x = 0; x < output.length; x++) {

            if(x == pos) continue;


            BigDecimal result  = god.divide(new BigDecimal(val[x]), pre, RoundingMode.HALF_UP); //SCALE
            BigDecimal pResult = god.divide(new BigDecimal(val[x]), mc); //PRECISION

            int pow = pResult.precision() - pResult.scale() - 1;


            if(result.compareTo(BigDecimal.ONE) > -1) {

                if(pow < 10){
                  output[x] = num.format(result);
                }else{
                  exp.setMaximumFractionDigits(pre);
                  output[x] = exp.format(result);
                }

            }else{

                if(Math.abs(pow) <= pre){
                  output[x] = num.format(result);

                }else{
                  exp.setMaximumFractionDigits(pResult.scale());
                  output[x] = exp.format(pResult);
                }

            }


        }


    }


}
