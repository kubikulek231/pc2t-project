package main;

public class MovieLive extends AbstractMovie{
	
	private int Stars;
	
	public MovieLive(String name, String director, int year, String review, int stars) {
		super(name, director, year, review);
		Stars = stars;
	}
	
	public int getStars() {return Stars;}
	public boolean setStars(int stars) {
		if (stars < 0 || stars > 5) {return false;}
		Stars = stars;
		return true;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder(super.toString());
	    sb.append(", Stars: ").append(Stars);
	    return sb.toString();
	}

}
