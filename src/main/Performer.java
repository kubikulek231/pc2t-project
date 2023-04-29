package main;


public class Performer {
	private String Name;
	private String Surname;
	
	public Performer (String name, String surname) {
		Name = name;
		Surname = surname;
	}
	
	public void SetName(String name) {Name = name;}
	public void SetSurname(String surname) {Surname = surname;}
	
	public String GetName() {return Name;}
	public String GetSurname() {return Surname;}
	
    
    @Override
    public String toString() {
        return  "Name: '" + Name + '\'' +
                ", Surname: '" + Surname + '\'';
    }
}
