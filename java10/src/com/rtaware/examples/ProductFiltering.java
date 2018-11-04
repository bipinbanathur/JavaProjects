package com.rtaware.examples;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductFiltering
{
	public static void main(String[] args)
	{
		List<Product> productList = new ArrayList<>();
		productList.add(new Product(100,"Sheiffer", 890.50));
		productList.add(new Product(101,"Parker", 450.50));
		productList.add(new Product(102,"Hero", 125.50));
		productList.add(new Product(103,"Reynolds", 12.50));
		productList.add(new Product(104,"Rotomac", 18.50));
		productList.add(new Product(105,"Pilot", 67.50));
		productList.add(new Product(106,"Waterman", 1090.50));
		productList.add(new Product(107,"Camel", 14.50));
		productList.add(new Product(108,"Stick", 9.50));
		
		String 	productName = "Sheiffer";
		Integer quantity 	= 10;
		Stream<Product> productStream = productList.parallelStream();
		System.out.println("-------------------------------------");
		if(productStream.isParallel())
		System.out.println("Parallel Stream");
		System.out.println("-------------------------------------");
		//Collect Product Names		
		List<String> productNames = productStream		
		.map(product -> product.getProductName())
		.collect(Collectors.toList());
		productNames.forEach(System.out::println);
		
		System.out.println("-------------------------------------");
		productStream = productList.parallelStream();
		Optional<Double> totalPrice = productStream
		.filter(product ->  product.getProductName().equals(productName))
		.map(product -> product.getProductPrice() * quantity).findFirst();
		System.out.println(totalPrice.get());
		System.out.println("-------------------------------------");
		productStream = productList.parallelStream();

		OptionalDouble maxPrice = productStream
		.sequential()
		.mapToDouble(product -> product.getProductPrice()+ product.getProductPrice() * 21/100)
		.max();
		System.out.println(maxPrice.getAsDouble());
		//
		System.out.println("-------------------------------------");
		
		productStream = productList.parallelStream();

		productStream
		.sequential()
		.mapToDouble(product -> product.getProductPrice()+ product.getProductPrice() * 21/100)
		.sorted()
		.forEach(System.out::println);	
		System.out.println("-------------------------------------");
		
		productStream = productList.parallelStream();
		
		Double productSum = productStream
		.sequential()
		.reduce(0.0, (sum, p) ->
		{
			System.out.format("Accumulator: Sum = %s\tPerson = %s\n", sum, p);
			return sum += p.getProductPrice();
		}, 
		(sum1, sum2) ->
		{
			System.out.format("Combiner: sum1= %s; sum2= %s \n", sum1, sum2);
			return sum1 + sum2;
		});

		System.out.println(productSum);
	}

}
