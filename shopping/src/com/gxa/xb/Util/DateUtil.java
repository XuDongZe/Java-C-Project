package com.gxa.xb.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date Str2Date(String str) {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 ");
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        //必须捕获异常
        try {
            date=simpleDateFormat.parse(str);
            System.out.println(date);
        } catch(ParseException px) {
            px.printStackTrace();
        }
        
		return date;
	}
	
	public static String getDateStr() {
		Date now=new Date();
		SimpleDateFormat f = new SimpleDateFormat( "yyyy年MM月dd日" );

		return (f.format(now));
	}
}
