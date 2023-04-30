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
        StringBuilder sb = new StringBuilder();
        sb.append("Name: '").append(Name).append('\'')
          .append(", Surname: '").append(Surname).append('\'');
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof Performer)) {
            return false;
        }
    	Performer performer = (Performer)obj;
    	if (performer.getName().equals(getName()) &&
    			performer.getSurname().equals(getSurname())) {
    		return true;
    	}
    	return false;
    }
}
