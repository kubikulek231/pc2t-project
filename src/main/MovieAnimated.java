package main;
public class MovieAnimated extends AbstractMovie{
	
    private int Rating;
    private int Age;
    
	public MovieAnimated(String name, String director, int year, String review, int rating, int age) {
		super(name, director, year, review);
		Rating = rating;
		Age = age;
	}
	
	public int getRating() {return Rating;}
	public int getAge() {return Age;}
	
	public void setRating(int rating) {Rating = rating;}
	public void setAge(int age) {Age = age;}
	
	@Override
    public String toString() {
        return super.toString() +
                ", Rating: " + Rating +
                ", Age: " + Age;
    }

}
