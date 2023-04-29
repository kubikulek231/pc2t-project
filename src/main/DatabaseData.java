package main;

import java.util.ArrayList;

public class DatabaseData {
	private ArrayList<ArrayList<Performer>> Animators = null;
	private ArrayList<ArrayList<Performer>> Actors = null;
	private ArrayList<MovieAnimated> moviesAnimated = null;
	private ArrayList<MovieLive> moviesLive = null;
	
	public DatabaseData() {}
	
    public ArrayList<ArrayList<Performer>> getAnimators() {return Animators;}
    public ArrayList<ArrayList<Performer>> getActors() {return Actors;}
    public ArrayList<MovieAnimated> getMoviesAnimated() {return moviesAnimated;}
    public ArrayList<MovieLive> getMoviesLive() {return moviesLive;}
    
    
	public void loadData() {
		try (DatabaseHandler databaseHandler = new DatabaseHandler()) {
			databaseHandler.connect();
			moviesLive = databaseHandler.loadMoviesLive();
			moviesAnimated = databaseHandler.loadMoviesAnimated();
			Animators = databaseHandler.loadAnimatorsMoviesAnimated();
			Actors = databaseHandler.loadActorsMoviesLive();
		} catch (Exception e) {
			System.out.println("an error occeured while loading data: " + e.getMessage());
		}
	}
	
	public void saveData() {
		try (DatabaseHandler databaseHandler = new DatabaseHandler()) {
			databaseHandler.connect();
			databaseHandler.saveMoviesLive(moviesLive);
			databaseHandler.saveMoviesAnimated(moviesAnimated);
			databaseHandler.saveActorsMoviesLive(Actors);
			databaseHandler.saveAnimatorsMoviesAnimated(Animators);
		} catch (Exception e) {
			System.out.println("an error occeured while saving data: " + e.getMessage());
		}
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
