
public class MovieAnimated extends AbstractMovie{
	
    private int Rating;
    private int Age;
    
	public MovieAnimated(int id, char name, char director, char year, char review, int rating, int age) {
		super(id, name, director, year, review);
		Rating = rating;
		Age = age;
	}
	
	public int getRating() {return Rating;}
	public int getAge() {return Age;}
	
	public void setRating(int rating) {Rating = rating;}
	public void setAge(int age) {Age = age;}

}
