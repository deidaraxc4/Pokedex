import java.util.ArrayList;

public class Pokemon {
	String nationalID;
	String name;
	String type1;
	String type2;
	ArrayList<String> type;
	public Pokemon(String id, String pokename) {
		nationalID = id;
		name = pokename;
	}
	
	public Pokemon(String id, String pokename, String type1)
	{
		nationalID = id;
		name = pokename;
		this.type1 = type1;
	}
	
	public Pokemon(String id, String pokename, String type1, String type2)
	{
		nationalID = id;
		name = pokename;
		this.type1 = type1;
		this.type2 = type2;
	}
	
	public Pokemon(String id, String pokename, ArrayList<String> types) {
		nationalID = id;
		name = pokename;
		type = types;
	}
	
	
	public String getID() {
		return nationalID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType1() {
		return type.get(0);
	}
	
	public String getType2() {
		return type.get(1);
	}
	
	public boolean isMonoType() {
		if(type.size()==1) {
			return true;
		} else {
			return false;
		}
	}

}
