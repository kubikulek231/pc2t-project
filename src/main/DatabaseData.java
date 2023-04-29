package main;

import java.util.ArrayList;

public class DatabaseData {
	private ArrayList<ArrayList<Performer>> Animators = null;
	private ArrayList<ArrayList<Performer>> Actors = null;
	private ArrayList<MovieAnimated> moviesAnimated = null;
	private ArrayList<MovieLive> moviesLive = null;
	
	public DatabaseData() {
		
	}
	
	public void loadData() {
		try (DatabaseHandler databaseHandler = new DatabaseHandler()) {
			databaseHandler.connect();
			moviesLive = databaseHandler.loadMoviesLive();
			moviesAnimated = databaseHandler.loadMoviesAnimated();
			Animators = databaseHandler.loadAnimatorsMoviesAnimated();
			Actors = databaseHandler.loadActorsMoviesLive();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void saveData() {
		
	}
	
	@Override
    public String toString() {
		String returnString = "";
		returnString = returnString + "Animators: \n";
		for (ArrayList<Performer> movieAnimated : Animators) {
			returnString = returnString + movieAnimated + "\n";
		}
		returnString = returnString + "Actors: \n";
		for (ArrayList<Performer> movieLive : Actors) {
			returnString = returnString + movieLive + "\n";
		}
		returnString = returnString + "Animated movies: \n";
		for (MovieAnimated movieAnimated : moviesAnimated) {
			returnString = returnString + movieAnimated + "\n";
		}
		returnString = returnString + "Live movies: \n";
		for (MovieLive movieLive : moviesLive) {
			returnString = returnString + movieLive + "\n";
		}
		return returnString;
    }
}
