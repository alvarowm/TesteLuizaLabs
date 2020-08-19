package br.com.alvaro.desafio.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date adicionarDia(Date date) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		return date;
	}

}
