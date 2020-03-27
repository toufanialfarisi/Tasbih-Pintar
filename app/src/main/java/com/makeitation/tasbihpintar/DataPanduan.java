package com.makeitation.tasbihpintar;

import java.util.ArrayList;

public class DataPanduan {




    public static String[] namaDzikir = {
            "Istigfar",
            "Al-fatihah",
            "Ayat Kursi",
            "Tasbih",
            "Tahmid",
            "Takbir",
            "Tahlil",
    };

    public static int[] gambar = {
            R.drawable.next,
            R.drawable.next,
            R.drawable.next,
            R.drawable.next,
            R.drawable.next,
            R.drawable.next,
            R.drawable.next
    };

    public static String[] keterangan = {
            "keterangan",
            "keterangan",
            "keterangan",
            "keterangan",
            "keterangan",
            "keterangan",
            "keterangan"
    };

    public static int[] number = {};



    static ArrayList<DataController> getData(){

        ArrayList<DataController> list = new ArrayList<>();
        for (int index = 0; index < gambar.length; index++){
            DataController data = new DataController();
            data.setGambar(String.valueOf(gambar[index]));
            data.setKeterangan(keterangan[index]);
            data.setNamaDzikir(namaDzikir[index]);
            data.setNumberItem(index+1);
            list.add(data);
        }
        return list;
    }

}
