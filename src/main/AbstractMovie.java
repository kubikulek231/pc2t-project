package main;

public abstract class AbstractMovie {
	
	private String Name;
	private String Director;
	private int Year;
	private String Review;
	
	public AbstractMovie (String name, String director, int year, String review) {
		Name = name;
		Director = director;
		Year = year;
		Review = review;
	}
	
	public boolean setName(String name) {
		if (name.length() > 128 || name.length() < 1) {return false;}
		Name = name;
		return true;
	}
	public boolean setDirector(String director) {
		if (director.length() > 64 || director.length() < 1) {return false;}
		Director = director;
		return true;
	}
	public boolean setYear(int year) {
		if (year < 0 || year > 2100) {return false;}
		Year = year;
		return true;
	}
	public boolean setReview(String review) {
		if (review.length() > 128) {return false;}
		Review = review;
		return true;
	}
	
	public String getName() {return Name;}
	public String getDirector() {return Director;}
	public int getYear() {return Year;}
	public String getReview() {return Review;}
	

    @Override
    public String toString() {
        return  "Name: '" + Name + '\'' +
                ", Director: '" + Director + '\'' +
                ", Year: " + Year +
                ", Review: '" + Review + '\'';
    }
}
