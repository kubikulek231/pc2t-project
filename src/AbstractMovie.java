
public abstract class AbstractMovie {
	
	private int Id;
	private char Name;
	private char Director;
	private int Year;
	private char Review;
	
	public AbstractMovie (int id, char name, char director, char year, char review) {
		Id = id;
		Name = name;
		Director = director;
		Year = year;
		Review = review;
		MovieState = State.UNEDITED;
	}
	
	public void SetId(int id) {Id = id;}
	public void SetName(char name) {Name = name;}
	public void SetDirector(char director) {Director = director;}
	public void SetYear(int year) {Year = year;}
	public void SetReview(char review) {Review = review;}
	
	public int GetId() {return Id;}
	public char GetName() {return Name;}
	public char GetDirector() {return Director;}
	public int GetYear() {return Year;}
	public char GetReview() {return Review;}
	
	public State MovieState;
	
    public enum State {
    	UNEDITED,
    	EDITED,
    	DELETED,
    	NEW,
    }
    
    @Override
    public String toString() {
        return  "Id: " + Id +
                ", Name: '" + Name + '\'' +
                ", Director: '" + Director + '\'' +
                ", Year: " + Year +
                ", Review: '" + Review + '\'';
    }
}
