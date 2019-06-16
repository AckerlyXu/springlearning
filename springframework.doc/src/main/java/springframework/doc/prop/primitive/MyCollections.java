package springframework.doc.prop.primitive;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class MyCollections {
      private  List list;
      private Map map;
      private Set set;
      @Autowired
      private PureDatasource pureDatasource;
      
      
      public PureDatasource getPureDatasource() {
    	  return pureDatasource;
      }
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public Set getSet() {
		return set;
	}
	public void setSet(Set set) {
		this.set = set;
	}
      
}
