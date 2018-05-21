package com.lifeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceImplApplication {
	private static volatile boolean running = true;
	public static void main(String[] args) {
		SpringApplication.run(ServiceImplApplication.class, args);
		synchronized (ServiceImplApplication.class)
		{
			while (running)
			{
				try
				{
					ServiceImplApplication.class.wait();
				}
				catch (final Throwable e)
				{
				}
			}
		}
	}
}
