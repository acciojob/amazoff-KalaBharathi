package com.driver;

import java.util.Arrays;
import java.util.List;

public class TimeUtils {
    public static int convertDeliveryTime(String deliveryTime){
        List<String> list= Arrays.asList(deliveryTime.split(":"));
        int HH=Integer.parseInt(list.get(0));
        int MM=Integer.parseInt(list.get(1));
        return (HH*60)+MM;
    }
    public static String convertDeliveryTime(int deliveryTime){
        int HH=deliveryTime/60;
        int MM=deliveryTime%60;
        String hh=String.valueOf(HH);
        String mm=String.valueOf(MM);
        if(hh.length()==1) hh='0'+hh;
        if(mm.length()==1) mm='0'+mm;
        return hh+":"+mm;
    }
}
