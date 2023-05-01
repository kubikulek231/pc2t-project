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
	
	public int getMovieIndex(AbstractMovie movie) {
		if (movie instanceof MovieLive) {
			return databaseData.getMoviesLive().indexOf(movie);
		}
		return databaseData.getMoviesAnimated().indexOf(movie);
	}
	
	public boolean deleteMovieLive(MovieLive movie) {
		if (movie == null) {return false;}
		if ((databaseData.getActors().remove(getMovieIndex(movie))) == null) {return false;}
		if (!databaseData.getMoviesLive().remove(movie)) {return false;}
		return true;
	}
	
	public boolean deleteMovieAnimated(MovieAnimated movie) {
		if (movie == null) {return false;}
		if ((databaseData.getAnimators().remove(getMovieIndex(movie))) == null) {return false;}
		if (!databaseData.getMoviesAnimated().remove(movie)) {return false;}
		return true;
	}
	
	public boolean addMovieLiveActor(int movieIndex, String name, String surname) {
		if (findPerformer(databaseData.getActors().get(movieIndex), name, surname) != null) {return false;}
		databaseData.getActors().get(movieIndex).add(new Performer(name, surname));
		return true;
	}
	
	public boolean deleteMovieLiveActor(int movieIndex, String name, String surname) {
		Performer performerToDelete = findPerformer(databaseData.getActors().get(movieIndex), name, surname);
		if (performerToDelete == null) {return false;}
		databaseData.getActors().get(movieIndex).remove(performerToDelete);
		return true;
	}
	
	public boolean deleteMovieAnimatedAnimator(int movieIndex, String name, String surname) {
		Performer performerToDelete = findPerformer(databaseData.getAnimators().get(movieIndex), name, surname);
		if (performerToDelete == null) {return false;}
		databaseData.getAnimators().get(movieIndex).remove(performerToDelete);
		return true;
	}
	public boolean hasMovieAnyPerformers(AbstractMovie movie) {
		if (movie instanceof MovieLive) {
			ArrayList<Performer> performers = databaseData.getActors().get(getMovieIndex((MovieLive)movie));
			if (performers == null || performers.isEmpty()) {return false;}
			return true;
		}
		ArrayList<Performer> performers = databaseData.getAnimators().get(getMovieIndex((MovieAnimated)movie));
		if (performers == null || performers.isEmpty()) {return false;}
		return true;
	}
	
	public boolean deleteMoviePerformers(AbstractMovie movie) {
		if (movie instanceof MovieLive) {
			ArrayList<Performer> performersToDelete = databaseData.getActors().get(getMovieIndex((MovieLive)movie));
			if (performersToDelete == null || performersToDelete.isEmpty()) {return false;}
			performersToDelete.clear();
			return true;
		}
		ArrayList<Performer> performersToDelete = databaseData.getAnimators().get(getMovieIndex((MovieAnimated)movie));
		if (performersToDelete == null || performersToDelete.isEmpty()) {return false;}
		performersToDelete.clear();
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
    	return databaseData.getActors().get(getMovieIndex(movie));
    }
    
    public ArrayList<Performer> getMovieAnimatedAnimators(MovieAnimated movie) {
    	return databaseData.getAnimators().get(getMovieIndex(movie));
    }
    
    public ArrayList<Performer> getMovieLiveActors(int index) {
    	return databaseData.getActors().get(index);
    }
    
    public ArrayList<Performer> getMovieAnimatedAnimators(int index) {
    	return databaseData.getAnimators().get(index);
    }
    
    public ArrayList<MovieAnimated> animatorStarrs(String name, String surname) {
        ArrayList<MovieAnimated> returnList = new ArrayList<MovieAnimated>();
        ArrayList<ArrayList<Performer>> animators = databaseData.getAnimators();
        for (int i = 0; i < animators.size(); i++) {
            ArrayList<Performer> actors = animators.get(i);
            for (int j = 0; j < actors.size(); j++) {
                Performer actor = actors.get(j);
                if (actor.getName().equals(name) && actor.getSurname().equals(surname)) {
                    returnList.add(databaseData.getMoviesAnimated().get(i));
                }
            }
        }
        return returnList;
    }

    public ArrayList<MovieLive> actorStarrs(String name, String surname) {
        ArrayList<MovieLive> returnList = new ArrayList<MovieLive>();
        ArrayList<ArrayList<Performer>> actorsList = databaseData.getActors();
        for (int i = 0; i < actorsList.size(); i++) {
            ArrayList<Performer> actors = actorsList.get(i);
            for (int j = 0; j < actors.size(); j++) {
                Performer actor = actors.get(j);
                if (actor.getName().equals(name) && actor.getSurname().equals(surname)) {
                    returnList.add(databaseData.getMoviesLive().get(i));
                }
            }
        }
        return returnList;
    }

}
