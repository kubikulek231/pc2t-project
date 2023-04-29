package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
	Scanner scanner;
	DatabaseData databaseData;
	ControlData controlData;
	private volatile boolean running;
	
	public App(DatabaseData databasedata) {
		databaseData = databasedata;
		controlData = new ControlData(databaseData);
		scanner = new Scanner(System.in);
		running = true;
	}
	
	private int scanInt() {
		int choice = 0;
		try {
		    choice = scanner.nextInt();
		} catch (InputMismatchException e) {
		    System.out.println("Invalid input. Please enter a number.");
		    scanner.next();
		}
		return choice;
	}
	
	public void run() {
		while (true) {
		System.out.println(
		"\n< Movie Database App >\n" +
		"1 ...... add a new movie\n" +
		"2 ...... search for a movie\n" +
		"3 ...... search for an actor\n" +
		"4 ...... search for an animator\n" +
		"5 ...... show all movies\n" +
		"6 ...... show all live movies\n" +
		"7 ...... show all animated movies\n" +
		"8 ...... show actors who starred in >1 movie\n" +
		"9 ...... save and exit\n" +
		"Enter a number: ");
			switch (scanInt()) {
		    case 1:
		    	addMovie();
		        break;
		    case 2:
		        break;
		    case 3:
		        break;
		    case 4:
		        break;
		    case 5:
		        break;
		    case 6:
		        break;
		    case 7:
		        break;
		    case 8:
		        break;
		    case 9:
		    	return;
		    default:
		        System.out.println("Invalid choice. Please try again.");
		        break;
			}
		}
	}
	private void addMovie() {
		while (running) {
			scanInt();
		}
		running = true;
	}
}
