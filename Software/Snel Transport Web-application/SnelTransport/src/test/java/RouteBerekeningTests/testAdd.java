package RouteBerekeningTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testAdd {

	@Test
	public void test() {
		
		
		List<Integer> list = new ArrayList<>();
		for (int iter = 1; iter<10 ; iter++) {
			list.add(iter);
		}
		
		System.out.println(list);
		list.add(0,100);
		System.out.println(list);
		list.add(list.size(),100);
		System.out.println(list);
		fail("Not yet implemented");
	}

}
