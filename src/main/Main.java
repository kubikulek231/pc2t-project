package main;

public class Main {
    public static void main(String[] args) {
    	DatabaseData databaseData = new DatabaseData();
    	databaseData.loadData();
    	App app = new App(databaseData);
    	app.run();
    	databaseData.saveData();
    	System.exit(0);
    }
}