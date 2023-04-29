package main;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    	DatabaseData databaseData = new DatabaseData();
    	databaseData.loadData();
    	System.out.println(databaseData);
    }
}