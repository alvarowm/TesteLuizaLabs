package br.com.alvaro.desafio.tests;

import br.com.alvaro.desafio.utils.DateUtils;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTests {

	@Test
	public void testDataMaisUmDia01 () throws ParseException {
		SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = dateformat3.parse("2010-10-01");
		Date date2 = DateUtils.adicionarDia(date1);
		Assert.assertEquals(date2.toString().split(" ")[2], "02");
	}
	
	@Test
	public void testDataMaisUmDia30 () throws ParseException {
		SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = dateformat3.parse("2010-08-30");
		Date date2 = DateUtils.adicionarDia(date1);
		Assert.assertEquals(date2.toString().split(" ")[2], "31");
	}
	
	@Test
	public void testDataMaisUmDia31 () throws ParseException {
		SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = dateformat3.parse("2010-08-31");
		Date date2 = DateUtils.adicionarDia(date1);
		Assert.assertEquals(date2.toString().split(" ")[2], "01");
		Assert.assertEquals(date2.toString().split(" ")[1], "Sep");
	}
	
}


