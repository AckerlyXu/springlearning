package springframework.doc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Society {

	private String name;

	public static String Advisors = "advisors";
	public static String President = "president";

	private List<Inventor> members;
	private Map officers = new HashMap();

	public Society() {
		members = new ArrayList<Inventor>();
		Inventor inventor = new Inventor();
		inventor.setName("mike");
		members.add(inventor);

		inventor = new Inventor();
		inventor.setName("jerry");
		members.add(inventor);

		inventor = new Inventor();
		inventor.setName("nicy");
		members.add(inventor);

		inventor = new Inventor();
		inventor.setName("mili");
		members.add(inventor);

		inventor = new Inventor();
		inventor.setName("keke");
		members.add(inventor);
	}

	public List getMembers() {
		return members;
	}

	public Map getOfficers() {
		return officers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMember(String name) {
		for (Inventor inventor : members) {
			if (inventor.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
