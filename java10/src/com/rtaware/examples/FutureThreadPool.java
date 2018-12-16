package com.rtaware.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class FutureThreadPool
{
	public static void main(String[] args)
	{
		try
		{
			System.out.println("Available Cores : "+Runtime.getRuntime().availableProcessors());
			ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			
			
			List<Future<Integer>> futureTasks = new ArrayList<>();
			
			IntStream.range(0,100).forEach(i->
			{
				Future<Integer> future = service.submit(new Task());
				futureTasks.add(future);
			});
			
			
			futureTasks.forEach( future -> 
			{
				int taskSize = 0;
				try
				{
					System.out.println("Future Result : ( "+taskSize + " )"+future.get());
					taskSize += 1;
				}
				catch (InterruptedException  | ExecutionException e)
				{
					e.printStackTrace();
				}
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}

class Task implements Callable<Integer>
{
	@Override
	public Integer call() throws Exception
	{
		Thread.sleep(1000);
		return new Random().nextInt();
	}	
}
