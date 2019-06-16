package springframework.doc;

import java.util.Properties;

import org.junit.Assert;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import springframework.doc.construct.ThingOne;
import springframework.doc.construct.ThingTwo;
import springframework.doc.convert.DependsonExotic;
import springframework.doc.dao.JpaItemDao;
import springframework.doc.dao.impl.JpaItemDaoImpl.InnerClass;
import springframework.doc.factory.FactoryProduct;
import springframework.doc.lifecycle.LifeCycleBean;
import springframework.doc.mutiscope.MyPrototypeBean;
import springframework.doc.mutiscope.MySingleBean;
import springframework.doc.profile.ProfileConfig;
import springframework.doc.prop.primitive.MyCollections;
import springframework.doc.prop.primitive.PureDatasource;
import springframework.doc.quality.Movie;
import springframework.doc.quality.MovieFinder;
import springframework.doc.relationship.ChildBean;
import springframework.doc.scan.FactoryMethodComponent;
import springframework.doc.service.PetStoreService;
import springframework.doc.service.impl.PetStoreServiceImpl;

public class Test {

	@org.junit.Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("service.xml", "dao.xml");
		PetStoreService service = context.getBean(PetStoreServiceImpl.class);
		System.out.println(service.service());
	}

	/**
	 * ͨ��import�Ķ�ȡ���xml������
	 */
	@org.junit.Test
	public void testImportResource() {
		ApplicationContext context = new ClassPathXmlApplicationContext("combine.xml");
		PetStoreService service = (PetStoreService) context.getBean("alias5");
		InnerClass inner = context.getBean(InnerClass.class);
		System.out.println(service.service());
		System.out.println(inner.getName());
	}

	/**
	 * ͨ������applicationContext��ʼ������
	 */
	@org.junit.Test
	public void testReaderDelegate() {

		// ����һ�������context,����ȷ���ú��ַ�ʽ��ȡbean����
		GenericApplicationContext context = new GenericApplicationContext();

		// ͨ��xmlReader��ȡxml�ļ�
		new XmlBeanDefinitionReader(context).loadBeanDefinitions("service.xml", "dao.xml");

		// ��ȡ���ˢ��context
		context.refresh();
		PetStoreService service = context.getBean(PetStoreServiceImpl.class);
		System.out.println(service.service());

	}

	/**
	 * ���Ծ�̬������Ĺ�������
	 */
	@org.junit.Test
	public void testStaticFactortMethod() {
		ApplicationContext context = new ClassPathXmlApplicationContext("combine.xml");
		FactoryProduct service = (FactoryProduct) context.getBean("product");

		System.out.println(service.getProduct());

	}

	/**
	 * ����ʵ��������Ĺ�������
	 */
	@org.junit.Test
	public void testInstanceFactortMethod() {
		ApplicationContext context = new ClassPathXmlApplicationContext("combine.xml");
		JpaItemDao dao = (JpaItemDao) context.getBean("fItemDao");
		PetStoreServiceImpl service = (PetStoreServiceImpl) context.getBean("fPetService");
		System.out.println(dao.getItemName());
		Assert.assertNull(service.getItemDao());

	}

	/**
	 * ���Թ��캯��ע��(�����ͺ͸�������)
	 */
	@org.junit.Test
	public void testConstructDependency() {
		ApplicationContext context = new ClassPathXmlApplicationContext("combine.xml");
		ThingOne thingOne = context.getBean(ThingOne.class);
		ThingTwo two = context.getBean(ThingTwo.class);
		System.out.println(thingOne.getCount());
		System.out.println(two.getInfo());

	}

	/**
	 * ����ע�루�������ϣ�
	 */

	@org.junit.Test
	public void testPorpPrivitive() {
		ApplicationContext context = new ClassPathXmlApplicationContext("prop_primitive.xml");
		PureDatasource pureDatasource = context.getBean(PureDatasource.class);
		Properties props = pureDatasource.getProps();
		MyCollections collections = context.getBean(MyCollections.class);
		System.out.println(collections.getList().toArray());
		System.out.println(collections.getSet().size());
		System.out.println(collections.getMap().keySet().size());
		System.out.println(pureDatasource + props.getProperty("jdbc.driver.className") + props.getProperty("jdbc.url"));

		Assert.assertNotNull(collections.getPureDatasource());
	}

	/**
	 * ����singleTon ������protoType�Ƿ���ͨ��look-upÿ�η��ز�ͬ��bean
	 */

	@org.junit.Test
	public void testmutiscope() {
		ApplicationContext mutiscope = new ClassPathXmlApplicationContext("mutiscope.xml");
		MySingleBean single = mutiscope.getBean(MySingleBean.class);
		MyPrototypeBean proto1 = single.getRandom();
		MyPrototypeBean proto2 = single.getRandom();
		Assert.assertNotEquals(proto1, proto2);

	}

	/**
	 * ����spring�������ڻص�
	 */
	@org.junit.Test
	public void testLifecycle() {

		ClassPathXmlApplicationContext lifecycle = new ClassPathXmlApplicationContext("lifecycle.xml");
		LifeCycleBean lifecycleBean = lifecycle.getBean(LifeCycleBean.class);

		System.out.println(lifecycleBean.getName() + lifecycleBean.getCode());
		// lifecycle.refresh();
		lifecycle.start();
		// lifecycle.refresh();
		// lifecycle.refresh();
		// lifecycle.refresh();
		// lifecycle.stop();
		lifecycle.close();

	}

	/**
	 * ����bean����ļ̳�
	 */
	@org.junit.Test
	public void testExtension() {

		ClassPathXmlApplicationContext relationship = new ClassPathXmlApplicationContext("relationship.xml");
		ChildBean child = relationship.getBean(ChildBean.class);

		System.out.println(child);

	}

	/**
	 * ����bean qualify
	 */
//	@org.junit.Test
	public void testQualify() {

		ClassPathXmlApplicationContext qualify = new ClassPathXmlApplicationContext("quality.xml");
		MovieFinder movieFinder = qualify.getBean(MovieFinder.class);

		System.out.println(movieFinder.getMovie());

	}

	/**
	 * ����componentScan
	 * 
	 */
	@org.junit.Test
	public void testScan() {

		ClassPathXmlApplicationContext scan = new ClassPathXmlApplicationContext("scan.xml");
		MovieFinder movieFinder = scan.getBean(MovieFinder.class);
		FactoryMethodComponent factory = scan.getBean(FactoryMethodComponent.class);
		System.out.println(factory.getClass().getSimpleName());
		PetStoreServiceImpl petService = scan.getBean(PetStoreServiceImpl.class);
		JpaItemDao itemDao2 = petService.getItemDao();
		JpaItemDao itemDao = petService.getItemDao();
		System.out.println(itemDao2.hashCode() == itemDao.hashCode());
		System.out.println(itemDao.getClass().getSimpleName());

	}

	/**
	 * ����profile����
	 * 
	 */
	@org.junit.Test
	public void testProfile() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("dev");
		context.register(ProfileConfig.class);
		context.refresh();
		Movie movie = context.getBean(Movie.class);
		// MovieFinder movieFinder = context.getBean(MovieFinder.class);
		System.out.println(movie);
		// System.out.println(movieFinder);
		String property = context.getEnvironment().getProperty("drivername");
		System.out.println("property:" + property);

	}

	/**
	 * �����Զ���PropertyEditor
	 * 
	 */
	@org.junit.Test
	public void testConvert() {

		ClassPathXmlApplicationContext scan = new ClassPathXmlApplicationContext("convert.xml");
		DependsonExotic dependsonExotic = scan.getBean(DependsonExotic.class);

		System.out.println(dependsonExotic.getExoticType().getName());

	}

}
