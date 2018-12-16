//package com.rtaware.examples;
//import java.lang.Runtime.Version;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//public class VersionInformation
//{
//	
//	private static void listOperations()
//	{
//		List<String> actors = new ArrayList<>();
//		actors.add("Jack Nicholson");
//		actors.add("Marlon Brando");
//		System.out.println(actors); // prints [Jack Nicholson, Marlon Brando]
//		
//		
//		// New API added - Creates an UnModifiable List from a List.
//		List<String> copyOfActors = List.copyOf(actors);
//		System.out.println(copyOfActors); // prints [Jack Nicholson, Marlon Brando]
//		// copyOfActors.add("Robert De Niro"); Will generate an
//		// UnsupportedOperationException
//		actors.add("Robert De Niro");
//		System.out.println(actors);// prints [Jack Nicholson, Marlon Brando, Robert De Niro]
//		System.out.println(copyOfActors); // prints [Jack Nicholson, Marlon Brando]
//				
//		String str = "";
//		Optional<String> name = Optional.ofNullable(str);
//		// New API added - is preferred option then get() method
//		name.orElseThrow(); // same as name.get()  
//
//		// New API added - Collectors.toUnmodifiableList
//		List<String> collect = actors.stream().collect(Collectors.toUnmodifiableList());
//	System.out.println(collect);
//		// collect.add("Tom Hanks"); // Will generate an
//		// UnsupportedOperationException
//		
//	}
//	
//	private static void varDeclaration()
//	{
//		var numbers = List.of(1, 2, 3, 4, 5); // inferred value ArrayList<String>
//		// Index of Enhanced For Loop
//		for (var number : numbers) {
//			System.out.println(number);
//		}
//		// Local variable declared in a loop
//		for (var i = 0; i < numbers.size(); i++) {
//			System.out.println(numbers.get(i));
//		}
//	}
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args)
//	{
//		Version version = Runtime.version();
//		System.out.println(	"Major		:	" +version.major());
//		System.out.println(	"Major		:	" +version.minor());
//		System.out.println(	"Build		:	" +version.build());
//		System.out.println(	"Optional	:	" +version.optional());
//		System.out.println(	"Pre		:	" +version.pre());
//		System.out.println(	"Secuirty	:	" +version.security());
//		System.out.println(	"Feature	:	" +version.feature());
//		System.out.println(	"Interim 	:	" +version.interim());
//		System.out.println(	"Update		:	" +version.update());
//		System.out.println(	"Patch		:	" +version.patch());
//		
//		 varDeclaration();
//
//		 listOperations();
//	}
//}
