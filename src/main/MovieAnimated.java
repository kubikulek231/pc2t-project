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
	
	public boolean setRating(int rating) {
		if (rating < 0 || rating > 10) {return false;}
		Rating = rating;
		return true;
		}
	public boolean setAge(int age) {
		if (age < 0 || age > 99) {return false;}
		Age = age;
		return true;
		}
	
	@Override
    public String toString() {
        return super.toString() +
                ", Rating: " + Rating +
                ", Age: " + Age;
    }

}
