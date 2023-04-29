package main;

public class MovieLive extends AbstractMovie{
	
	private int Stars;
	
	public MovieLive(String name, String director, int year, String review, int stars) {
		super(name, director, year, review);
		Stars = stars;
	}
	
	public int GetStars() {return Stars;}
	public void setStars(int stars) {Stars = stars;}
	
	
	@Override
    public String toString() {
        return super.toString() +
                ", Stars: " + Stars;
    }
}
