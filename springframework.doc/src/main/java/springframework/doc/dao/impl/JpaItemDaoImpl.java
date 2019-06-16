package springframework.doc.dao.impl;

import springframework.doc.dao.JpaItemDao;

public class JpaItemDaoImpl implements JpaItemDao {
	private  String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static class InnerClass{
		public String getName() {
			return "InnerClass";
		}
	}
    public String getItemName() {
    	return "itemname";
    }
}
