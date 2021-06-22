package com.obulex.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
    *
    * @return
    */
   public static Calendar getToday()
   {
       return Calendar.getInstance();
   }

   /**
    *
    * @param amount
    * @return
    */
   public static Calendar genericDate(int amount)
   {
       Date date = new Date();
       Calendar yesterday = Calendar.getInstance();
       yesterday.setTime(date);
       yesterday.add(Calendar.DATE, amount);
       return yesterday;
   }
}
