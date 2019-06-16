package springframework.doc.factory;

import springframework.doc.dao.JpaAccountDao;
import springframework.doc.dao.JpaItemDao;
import springframework.doc.dao.impl.JpaAccountDaoImpl;
import springframework.doc.dao.impl.JpaItemDaoImpl;
import springframework.doc.service.PetStoreService;
import springframework.doc.service.impl.PetStoreServiceImpl;

public class MyFactory {
    public static FactoryProduct getProcuct(String name,int number) {
    	return new FactoryProduct(name,number);
    }
    public static PetStoreService service = new PetStoreServiceImpl();
    public static JpaItemDao itemDao = new JpaItemDaoImpl();
    public JpaItemDao getItem() {
    	return itemDao;
    }
    public PetStoreService getService() {
    	return service;
    }
    
    
}
