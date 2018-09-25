package com.convert.cv;


import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by ADEPT on 07.05.2018.
 */
public class Factory extends Application{


    private String[] length, area, volume, speed, weight, energy, time, data, temp;
    private int[] labelMas;
    private String[] mas;
    private String[] titleMas;
    private int id;
    private boolean scroll = false;

    private int digits = 3;
    private char divider = '.';

    public Factory() {
        super();
    }


    public void factory(Context c) {


        Resources res = c.getResources();

        length = res.getStringArray(R.array.length);
        area = res.getStringArray(R.array.area);
        volume = res.getStringArray(R.array.volume);
        speed = res.getStringArray(R.array.speed);
        weight = res.getStringArray(R.array.weight);
        energy = res.getStringArray(R.array.energy);
        time = res.getStringArray(R.array.time);
        data = res.getStringArray(R.array.data);
        temp = res.getStringArray(R.array.temp);

        titleMas = res.getStringArray(R.array.title);
    }



    void generate(int pos){

        id = pos;
        labelMas = null;

        switch (pos){
            case 0 :
             mas = length;
             labelMas = new int[]{0, 7, 11};
            break;

            case 1 :
             mas = area;
            break;

            case 2 :
             mas = volume;
             labelMas = new int[]{0, 6, 11};
            break;

            case 3 :
             mas = speed;
             labelMas = new int[]{0, 7, 9};
            break;

            case 4 :
             mas = weight;
             labelMas = new int[]{0, 7};
            break;

            case 5 :
             mas = energy;
            break;

            case 6 :
             mas = time;
            break;

            case 7 :
             mas = data;
             labelMas = new int[]{0, 8};
            break;

            case 8 :
             mas = temp;
            break;

        }

    }

    public void setScroll(boolean scroll){
        this.scroll = scroll;
    }

    public void setDigits(String d) {
        digits = Integer.parseInt(d);
    }

    public void setDivider(String divider) {
        this.divider = divider.charAt(0);
    }

    public boolean getScroll(){
        return scroll;
    }

    public int getId() {
        return id;
    }

    public int[] getLabelMas() {
        return labelMas;
    }

    public String[] getUnitMas() {
        return mas;
    }

    public String[] getTitleMas() {
        return titleMas;
    }

    public String getTitle() {
        return titleMas[id];
    }

    public int getDigits() {
        return digits;
    }

    public char getDivider() {
        return divider;
    }
}
