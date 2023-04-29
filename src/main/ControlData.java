package main;

import java.util.ArrayList;

public class ControlData {
	private DatabaseData databaseData;
	
	public ControlData(DatabaseData database) {
		databaseData = database;
    }

	private Performer findPerformer(ArrayList<Performer> performerArray, String name, String surname) {
		for (Performer performer : performerArray) {
			if (performer.getName().equals(name) && performer.getSurname().equals(surname)) {
				return performer;
			}
		}
		return null;
	}
	
	public MovieLive findMovieLive(String name) {
		for (MovieLive movie : databaseData.getMoviesLive()) {
			if (movie.getName().equals(name)) {
				return movie;
			}
		}
		return null;
	}
	
	public MovieAnimated findMovieAnimated(String name) {
		for (MovieAnimated movie : databaseData.getMoviesAnimated()) {
			if (movie.getName().equals(name)) {
				return movie;
			}
		}
		return null;
	}
	
	public int getMovieLiveIndex(MovieLive movie) {
		return databaseData.getMoviesLive().indexOf(movie);
	}
	
	public int getMovieAnimatedIndex(MovieAnimated movie) {
		return databaseData.getMoviesAnimated().indexOf(movie);
	}
	
	public boolean deleteMovieLive(MovieLive movie) {
		if (movie == null) {return false;}
		if ((databaseData.getActors().remove(getMovieLiveIndex(movie))) == null) {return false;}
		if (!databaseData.getMoviesLive().remove(movie)) {return false;}
		return true;
	}
	
	public boolean deleteMovieAnimated(MovieAnimated movie) {
		if (movie == null) {return false;}
		if ((databaseData.getAnimators().remove(getMovieAnimatedIndex(movie))) == null) {return false;}
		if (!databaseData.getMoviesAnimated().remove(movie)) {return false;}
		return true;
	}
	
	public boolean addMovieLiveActor(int movieIndex, String name, String surname) {
		if (findPerformer(databaseData.getActors().get(movieIndex), name, surname) != null) {return false;}
		databaseData.getActors().get(movieIndex).add(new Performer(name, surname));
		return true;
	}
	
	public boolean addMovieAnimatedAnimator(int movieIndex, String name, String surname) {
		if (findPerformer(databaseData.getAnimators().get(movieIndex), name, surname) != null) {return false;}
		databaseData.getAnimators().get(movieIndex).add(new Performer(name, surname));
		return true;
	}
	
    public boolean addMovieLive(String name, String director, int year, String review, int stars) {
    	if (findMovieLive(name) != null) {return false;}
    	databaseData.getActors().add(new ArrayList<Performer>());
    	databaseData.getMoviesLive().add(new MovieLive(name, director, year, review, stars));
    	return true;
    }
    
    public boolean addMovieAnimated(String name, String director, int year, String review, int rating, int age) {
    	if (findMovieAnimated(name) != null) {return false;}
    	databaseData.getAnimators().add(new ArrayList<Performer>());
    	databaseData.getMoviesAnimated().add(new MovieAnimated(name, director, year, review, rating, age));
    	return true;
    }
    
    public ArrayList<Performer> getMovieLiveActors(MovieLive movie) {
    	return databaseData.getActors().get(getMovieLiveIndex(movie));
    }
    
    public ArrayList<Performer> getMovieAnimatedAnimators(MovieAnimated movie) {
    	return databaseData.getAnimators().get(getMovieAnimatedIndex(movie));
    }
    
    public ArrayList<Performer> getMovieLiveActors(int index) {
    	return databaseData.getActors().get(index);
    }
    
    public ArrayList<Performer> getMovieAnimatedAnimators(int index) {
    	return databaseData.getAnimators().get(index);
    }
    
	public ArrayList<MovieAnimated> animatorStarrs(String name, String surname) {
		ArrayList<MovieAnimated> returnList = new ArrayList<MovieAnimated>();
		for (ArrayList<Performer> actors : databaseData.getAnimators()) {
			for (Performer actor : actors) {
				if (actor.getName().equals(name) && actor.getSurname().equals(surname)) {
					returnList.add(databaseData.getMoviesAnimated().get(databaseData.getAnimators().indexOf(actors)));
				}
			}
    		
		}
		return returnList;
	}
	
	public ArrayList<MovieLive> actorStarrs(String name, String surname) {
		ArrayList<MovieLive> returnList = new ArrayList<MovieLive>();
		for (ArrayList<Performer> actors : databaseData.getActors()) {
			for (Performer actor : actors) {
				if (actor.getName().equals(name) && actor.getSurname().equals(surname)) {
					returnList.add(databaseData.getMoviesLive().get(databaseData.getActors().indexOf(actors)));
				}
			}
    		
		}
		return returnList;
	}
}
