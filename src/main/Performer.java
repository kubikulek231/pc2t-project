package main;


public class Performer {
	private String Name;
	private String Surname;
	
	public Performer (String name, String surname) {
		Name = name;
		Surname = surname;
	}
	
	public boolean setName(String name) {
		if (name.length() > 64 || name.length() < 1) {return false;}
		Name = name;
		return true;
		}
	public boolean setSurname(String surname) {
		if (surname.length() > 64 || surname.length() < 1) {return false;}
		Name = surname;
		return true;
	}
	
	public String getName() {return Name;}
	public String getSurname() {return Surname;}
	
    
    @Override
    public String toString() {
        return  "Name: '" + Name + '\'' +
                ", Surname: '" + Surname + '\'';
    }
}
