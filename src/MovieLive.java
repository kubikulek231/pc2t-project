
public class MovieLive extends AbstractMovie{
	
	private int Stars;
	
	public MovieLive(int id, char name, char director, char year, char review, int stars) {
		super(id, name, director, year, review);
		Stars = stars;
	}
	
	public int GetStars() {return Stars;}

}
