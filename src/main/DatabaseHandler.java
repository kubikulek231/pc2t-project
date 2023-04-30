package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHandler implements AutoCloseable{
	private volatile Connection aliveConnection = null;
	
    public void connect() {
        Connection connection = null;
        try {
            String path = "jdbc:sqlite:sqlite/movie_database.db";
            connection = DriverManager.getConnection(path);
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        } finally {
        	System.out.println("successfully connected to the database");
        }
        aliveConnection = connection;
    }
    
    public ArrayList<MovieLive> loadMoviesLive() {
		ArrayList<MovieLive> returnMoviesLive = new ArrayList<MovieLive>();
        String query = "SELECT * FROM movies_live";
        Connection conn = aliveConnection;
        try {
        	Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
            	returnMoviesLive.add(new MovieLive(
                		resultSet.getString("mov_name"), 
                		resultSet.getString("mov_director"),
                		resultSet.getInt("mov_year"),
                		resultSet.getString("mov_review"),
                		resultSet.getInt("mov_stars")));
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
		}
        return returnMoviesLive;
    }
    
    public ArrayList<MovieAnimated> loadMoviesAnimated() {
		ArrayList<MovieAnimated> returnMoviesAnimated = new ArrayList<MovieAnimated>();
        String query = "SELECT * FROM movies_animated";
        Connection conn = aliveConnection;
        try {
        	Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                returnMoviesAnimated.add(new MovieAnimated(
                		resultSet.getString("mov_name"), 
                		resultSet.getString("mov_director"),
                		resultSet.getInt("mov_year"),
                		resultSet.getString("mov_review"),
                		resultSet.getInt("mov_rating"),
                		resultSet.getInt("mov_age")));
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
		}
        return returnMoviesAnimated;
    }
    

    public ArrayList<ArrayList<Performer>> loadActorsMoviesLive() {
    	int moviesLiveNum = loadMoviesLive().size();
    	ArrayList<ArrayList<Performer>> returnActorsMoviesLive = new ArrayList<ArrayList<Performer>>();
		for (int i = 1; i <= moviesLiveNum; i++) {
			ArrayList<Performer> singleMovieActors = new ArrayList<Performer>();
			String query = "SELECT * FROM actors WHERE act_movie_id = ? ";
	        try (PreparedStatement preparedStatement = aliveConnection.prepareStatement(query)) {
	        	preparedStatement.setInt(1, i);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()) {
	            	singleMovieActors.add(new Performer(
	                		resultSet.getString("act_name"), 
	                		resultSet.getString("act_surname")));
	            }
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
			}
	        returnActorsMoviesLive.add(singleMovieActors);
		}
        return returnActorsMoviesLive;
    }
    public ArrayList<ArrayList<Performer>> loadAnimatorsMoviesAnimated() {
    	int moviesLiveNum = loadMoviesAnimated().size();
    	ArrayList<ArrayList<Performer>> returnAnimatorsMoviesAnimated = new ArrayList<ArrayList<Performer>>();
		for (int i = 1; i <= moviesLiveNum; i++) {
			ArrayList<Performer> singleMovieAnimators = new ArrayList<Performer>();
			String query = "SELECT * FROM animators WHERE act_movie_id = ? ";
	        try (PreparedStatement preparedStatement = aliveConnection.prepareStatement(query)) {
	        	preparedStatement.setInt(1, i);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()) {
	            	singleMovieAnimators.add(new Performer(
	                		resultSet.getString("act_name"), 
	                		resultSet.getString("act_surname")));
	            }
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
			}
	        returnAnimatorsMoviesAnimated.add(singleMovieAnimators);
		}
        return returnAnimatorsMoviesAnimated;
    }

    public void saveMoviesLive(ArrayList<MovieLive> moviesLive) {
    	try {
			Statement statement = aliveConnection.createStatement();
			statement.executeUpdate("DELETE FROM movies_live");
		} catch (SQLException e) {
			System.out.println("an error occured during deletion of live movies: " + e.getMessage());
		}
    	String query = "INSERT INTO movies_live "
				+ "(mov_name, mov_director, mov_year, mov_review, mov_stars) VALUES (?, ?, ?, ?, ?)";
    	for (MovieLive movieLive : moviesLive) {
    		try (PreparedStatement preparedStatement = aliveConnection.prepareStatement(query)) {
	        	preparedStatement.setString(1, movieLive.getName());
	        	preparedStatement.setString(2, movieLive.getDirector());
	        	preparedStatement.setInt(3, movieLive.getYear());
	        	preparedStatement.setString(4, movieLive.getReview());
	        	preparedStatement.setInt(5, movieLive.getStars());
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	        	System.out.println("an error occured during saving live movies: " + e.getMessage());
			}
		}
    }
    
    public void saveMoviesAnimated(ArrayList<MovieAnimated> moviesAnimated) {
    	try {
			Statement statement = aliveConnection.createStatement();
			statement.executeUpdate("DELETE FROM movies_animated");
		} catch (SQLException e) {
			System.out.println("an error occured during deletion of animated movies: " + e.getMessage());
		}
    	String query = "INSERT INTO movies_animated "
				+ "(mov_name, mov_director, mov_year, mov_review, mov_rating, mov_age) VALUES (?, ?, ?, ?, ?, ?)";
    	for (MovieAnimated movieAnimated : moviesAnimated) {
    		try (PreparedStatement preparedStatement = aliveConnection.prepareStatement(query)) {
	        	preparedStatement.setString(1, movieAnimated.getName());
	        	preparedStatement.setString(2, movieAnimated.getDirector());
	        	preparedStatement.setInt(3, movieAnimated.getYear());
	        	preparedStatement.setString(4, movieAnimated.getReview());
	        	preparedStatement.setInt(5, movieAnimated.getRating());
	        	preparedStatement.setInt(6, movieAnimated.getAge());
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	        	System.out.println("an error occured during saving animated movies: " + e.getMessage());
			}
		}
    }
    
    public void saveActorsMoviesLive(ArrayList<ArrayList<Performer>> actorsMoviesLive) {
    	try {
			Statement statement = aliveConnection.createStatement();
			statement.executeUpdate("DELETE FROM actors");
		} catch (SQLException e) {
			System.out.println("an error occured during deletion of actors: " + e.getMessage());
		}
    	String query = "INSERT INTO actors "
				+ "(act_name, act_surname, act_movie_id) VALUES (?, ?, ?)";
    	int movieId = 1;
    	for (ArrayList<Performer> singleMovieActors : actorsMoviesLive) {
    		if (singleMovieActors == null) {continue;}
    		for (Performer actor : singleMovieActors) {
	    		try (PreparedStatement preparedStatement = aliveConnection.prepareStatement(query)) {
		        	preparedStatement.setString(1, actor.getName());
		        	preparedStatement.setString(2, actor.getSurname());
		        	preparedStatement.setInt(3, movieId);
		            preparedStatement.executeUpdate();
		        } catch (SQLException e) {
		        	System.out.println("an error occured during saving actors: " + e.getMessage());
				}
			}
    		movieId++;
    	}
    }
    
    public void saveAnimatorsMoviesAnimated(ArrayList<ArrayList<Performer>> animatorsMoviesAnimated) {
    	try {
			Statement statement = aliveConnection.createStatement();
			statement.executeUpdate("DELETE FROM animators");
		} catch (SQLException e) {
			System.out.println("an error occured during deletion of animators: " + e.getMessage());
		}
    	String query = "INSERT INTO animators "
				+ "(act_name, act_surname, act_movie_id) VALUES (?, ?, ?)";
    	int movieId = 1;
    	for (ArrayList<Performer> singleMovieAnimators : animatorsMoviesAnimated) {
    		if (singleMovieAnimators == null) {continue;}
    		for (Performer animator : singleMovieAnimators) {
	    		try (PreparedStatement preparedStatement = aliveConnection.prepareStatement(query)) {
		        	preparedStatement.setString(1, animator.getName());
		        	preparedStatement.setString(2, animator.getSurname());
		        	preparedStatement.setInt(3, movieId);
		            preparedStatement.executeUpdate();
		        } catch (SQLException e) {
		        	System.out.println("an error occured during saving animators: " + e.getMessage());
				}
			}
    		movieId++;
    	}
    }

	@Override
	public void close() throws Exception {
		if (aliveConnection != null && !aliveConnection.isClosed()) {
			aliveConnection.close();
            System.out.println("successfully closed the database connection");
        }
		
	}
}
