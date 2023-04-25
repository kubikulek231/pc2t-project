

public class Performer {
	private int Id;
	private char Name;
	private char Surname;
	private int Year;
	private int Month;
	private int Day;
	
	public Performer (int id, char name, char surname, char year, int month, int day) {
		Id = id;
		Name = name;
		Surname = surname;
		Year = year;
		Month = month;
		Day = day;
		MovieState = State.UNEDITED;
	}
	
	public void SetId(int id) {Id = id;}
	public void SetName(char name) {Name = name;}
	public void SetSurname(char surname) {Surname = surname;}
	public void SetYear(int year) {Year = year;}
	public void SetMonth(int month) {Month = month;}
	public void SetDay(int day) {Day = day;}
	
	public int GetId() {return Id;}
	public char GetName() {return Name;}
	public char GetSurname() {return Surname;}
	public int GetYear() {return Year;}
	public int GetMonth() {return Month;}
	public int GetDay() {return Day;}
	
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
                ", Surname: '" + Surname + '\'' +
                ", Birthdate: '" + Year + "-" + Month + "-" + Day + '\'';
    }
}
