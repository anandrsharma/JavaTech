package com.spring.ztest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CollectionTest col = new CollectionTest();
		col.check();
	}
	
	public void check() {
		Map<Integer, String> hmap = new HashMap<Integer, String>();
		List<String> list = new ArrayList<>();
		hmap.put(1,"One"); 	hmap.put(2,"Two");
		list.add("One"); 	
		list.add("Two");
		hmap.forEach((key,value)->System.out.println(key+" - "+value));
		list.stream().forEach((str) -> 
		{
			System.out.println("List :"+str);
		});
	}
}
