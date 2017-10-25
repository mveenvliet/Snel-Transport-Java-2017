package RouteBerekeningTests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateTest {

	@Test
	public void test() {
		
		
		String dt = "2008-01-31";  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		
		System.out.println(dt);
		fail("Not yet implemented");
	}

}
