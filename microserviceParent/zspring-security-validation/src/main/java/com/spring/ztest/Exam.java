package com.spring.ztest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Exam {

	public static void main(String[] args) {
		String input[][] = { {"Ram","87"}, {"Shyam","60"}, {"Ghanshyam","20"}, {"Ram","13"}, {"Shyam","30"} };
		String s = input.toString();
		System.out.println(s.toString());
		Map h = new HashMap();
		
		//Map<String, String> arrMap = Arrays.stream(input).collect(Collectors.toMap(e->e[0], e->e[1] ) );
		
		List<List<String>> list = Arrays.stream(input)
                .map(Arrays::asList)
                .collect(Collectors.toList());
		
		List ll = list.stream().flatMap(l->l.stream()).collect(Collectors.toList());
		System.out.println(list.toString());
		System.out.println(ll.toString());
		//Map<String, String> arrMat = Arrays.stream(input).collect(Collectors.toMap(e->e[0], e->e[1] ) );)
		
	}

}
