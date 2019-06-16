package springframework.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import springframework.doc.model.Color;
import springframework.doc.model.EnumContainer;
import springframework.doc.model.Inventor;
import springframework.doc.prop.primitive.PureDatasource;

public class SchemaTest {
	/**
	 * ²âÊÔ³£Á¿
	 */
	@Test
	public void test() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("utils.xml");
		Inventor bean = context.getBean(Inventor.class);
		assertEquals(bean.getName(), Inventor.MIKE);
	}

	/**
	 * ²âÊÔenum
	 */
	@Test
	public void testEnum() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("utils.xml");
		EnumContainer bean = context.getBean(EnumContainer.class);
		assertEquals(bean.getColor(), Color.BLUE);
	}

	/**
	 * ²âÊÔutil:property-path
	 */
	@Test
	public void testPropertyPath() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("utils.xml");
		Color bean = context.getBean(Color.class);
		assertEquals(bean, Color.BLUE);
	}

	/**
	 * ²âÊÔutil:property util
	 */
	@Test
	public void testPropertyLoad() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("utils.xml");
		PureDatasource bean = context.getBean(PureDatasource.class);
		assertTrue(bean.getProps().keySet().size() > 3);
	}
}
