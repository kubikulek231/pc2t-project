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
	
	public void SetName(String name) {Name = name;}
	public void SetDirector(String director) {Director = director;}
	public void SetYear(int year) {Year = year;}
	public void SetReview(String review) {Review = review;}
	
	public String GetName() {return Name;}
	public String GetDirector() {return Director;}
	public int GetYear() {return Year;}
	public String GetReview() {return Review;}
	

    @Override
    public String toString() {
        return  "Name: '" + Name + '\'' +
                ", Director: '" + Director + '\'' +
                ", Year: " + Year +
                ", Review: '" + Review + '\'';
    }
}
