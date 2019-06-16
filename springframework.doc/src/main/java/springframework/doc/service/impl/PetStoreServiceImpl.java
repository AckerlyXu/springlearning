package springframework.doc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import springframework.doc.dao.JpaAccountDao;
import springframework.doc.dao.JpaItemDao;
import springframework.doc.service.PetStoreService;

public class PetStoreServiceImpl implements PetStoreService {
	@Autowired
	private JpaItemDao itemDao;
	private JpaAccountDao accountDao;

	public JpaItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(JpaItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void setAccountDao(JpaAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public String service() {
		try {
			Thread.sleep(1234);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemDao == null ? "" : itemDao.getItemName() + "|" + accountDao == null ? "" : accountDao.getUsername();
	}
}
