package com.rtaware.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class StreamTest
{

	public static void main(String[] args)
	{
		Stream.of("Bipin","Ajay","Arun","Aswathi","Anuprabha","Akshay","Jagan")
		.map(String::toUpperCase)
		.filter(s-> {return s.startsWith("A");})
		.sorted()
		.forEach(System.out::println);
		
		List<Person> persons = Arrays.asList(
			    new Person("Max", 18),
			    new Person("Peter", 23),
			    new Person("Pamela", 23),
			    new Person("David", 12));
		
		List<Person> filtered =
			    persons
			        .stream()
			        .filter(p -> p.name.startsWith("P"))
			        .collect(Collectors.toList());

			System.out.println(filtered);

			persons
			    .parallelStream()
			    .reduce(0,
			        (sum, p) -> {
			            System.out.format("accumulator: sum=%s; person=%s [%s]\n",
			                sum, p, Thread.currentThread().getName());
			            return sum += p.age;
			        },
			        (sum1, sum2) -> {
			            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
			                sum1, sum2, Thread.currentThread().getName());
			            return sum1 + sum2;
			        });
	}

}
