package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
			String query = "SELECT a.act_name, a.act_surname " +
	                	   "FROM actors a " +
	                       "INNER JOIN movies_actors ma ON a.id = ma.actor_id " +
	                       "WHERE ma.movie_id = ?";
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
    	int moviesLiveNum = loadMoviesLive().size();
    	ArrayList<ArrayList<Performer>> returnAnimatorsMoviesAnimated = new ArrayList<ArrayList<Performer>>();
		for (int i = 1; i <= moviesLiveNum; i++) {
			ArrayList<Performer> singleMovieAnimators = new ArrayList<Performer>();
			String query = "SELECT a.act_name, a.act_surname " +
	                	   "FROM animators a " +
	                       "INNER JOIN movies_animators ma ON a.id = ma.animator_id " +
	                       "WHERE ma.movie_id = ?";
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
    public static void saveAllToDatabase (
    		Connection connection, 
    		List<MovieLive> moviesLive, 
    		List<List<Performer>> actorsMoviesLive, 
    		List<MovieAnimated> moviesAnimated, 
    		List<List<Performer>> animatorsMoviesAnimated) 
    {
    }

	@Override
	public void close() throws Exception {
		if (aliveConnection != null && !aliveConnection.isClosed()) {
			aliveConnection.close();
            System.out.println("successfully closed the database connection");
        }
		
	}
}
