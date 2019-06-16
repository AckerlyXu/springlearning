package springframework.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import springframework.doc.construct.ThingTwo;
import springframework.doc.model.Inventor;
import springframework.doc.model.PlaceOfBirth;
import springframework.doc.model.Society;

public class SpelTest {

	/**
	 * SpEl Hello World ����һ������
	 */
	@org.junit.Test
	public void spELHelloWorld() {

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("'Hello World'.concat('!')");
		String msg = (String) express.getValue();
		System.out.println(msg);
	}

	/**
	 * �������
	 */
	@org.junit.Test
	public void spELGetProperty() {

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("'Hello World'.bytes");
		byte[] msg = (byte[]) express.getValue();
		System.out.println(msg.length);
	}

	/**
	 * ���Ƕ�׵�����,�Լ����ù��캯����������
	 */
	@org.junit.Test
	public void spELGetNestedProperty() {

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("new String('Hello World').bytes.length");
		int msg = express.getValue(Integer.class);// �����getValue�д����˲�������ô�Ͳ���Ҫǿת��
		System.out.println(msg);
	}

	/**
	 * ����rootObject
	 */
	@org.junit.Test
	public void rootObject() {

		// Create and set a calendar
		GregorianCalendar c = new GregorianCalendar();
		c.set(1856, 7, 9);

		// The constructor arguments are name, birthday, and nationality.
		Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

		ExpressionParser parser = new SpelExpressionParser();

		Expression exp = parser.parseExpression("name");
		String value = exp.getValue(tesla, String.class);
		Assert.assertEquals(value, tesla.getName());

		Expression exp1 = parser.parseExpression("name == 'Nikola Tesla'");
		Boolean value2 = exp1.getValue(tesla, Boolean.class);
		Assert.assertTrue(value2);
	}

	/**
	 * evaluationContext
	 */
	@org.junit.Test
	public void evaluationContext() {

		ArrayList<Boolean> arrayList = new ArrayList<>();
		arrayList.add(true);

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("[0]");
		EvaluationContext context = new StandardEvaluationContext();
		express.setValue(context, arrayList, false);

		Assert.assertFalse(arrayList.get(0));
	}

	class Demo {
		public List<String> list;
	}

	/**
	 * ���� parser
	 */
	@org.junit.Test
	public void parserConfiguration() {

		Demo demo = new Demo();
		// ������������������nullֵ��ʱ���Ƿ��Զ���������
		// ���ĸ���������Ǳ�Խ���ʱ���Ƿ��Զ��򼯺������Ԫ��
		SpelParserConfiguration configuration = new SpelParserConfiguration(SpelCompilerMode.MIXED, null, true, true,
				Integer.MAX_VALUE);

		ExpressionParser parser = new SpelExpressionParser(configuration);
		Expression express = parser.parseExpression("list[5]");
		express.getValue(demo, String.class);

		Assert.assertEquals(6, demo.list.size());
	}

	/**
	 * ��xml��ʹ��spel���ʽ
	 */
	@org.junit.Test
	public void spelAndApplicationContext() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spel.xml");
		ThingTwo bean = context.getBean(ThingTwo.class);
		Inventor bean2 = context.getBean(Inventor.class);
		assertEquals(bean.getInfo(), bean2.getName());

	}

	/**
	 * ���� inLine collection
	 */
	@org.junit.Test
	public void collection() {

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("{1,2,3}");
		// Ϊ�����ܣ�ͬһ��parser��parse��ͬ���ַ������᷵����ͬ��list
		Assert.assertEquals(express.getValue(List.class), parser.parseExpression("{1,2,3}").getValue(List.class));

		Object map = parser.parseExpression("{'a':1,'b':'c'}").getValue();
		assertTrue(map instanceof Map);
		Expression ints = parser.parseExpression("new int[]{1,2,3}");
		assertTrue(ints.getValue() instanceof int[]);
	}

//	// evaluates to false
//	boolean falseValue = parser.parseExpression(
//	        "'xyz' instanceof T(Integer)").getValue(Boolean.class);
//
//	// evaluates to true
//	boolean trueValue = parser.parseExpression(
//	        "'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);
//
//	//evaluates to false
//	boolean falseValue = parser.parseExpression(
//	        "'5.0067' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);

	/**
	 * ��ֵ
	 */
	@org.junit.Test
	public void assign() {
		ExpressionParser parser = new SpelExpressionParser();
		Inventor vInventor = new Inventor();
		// ��һ��
		Object obj = parser.parseExpression("Name = 'mike'").getValue(vInventor);
		// �ڶ���
		parser.parseExpression("Name").setValue(vInventor, "jerry");
		System.out.println(vInventor.getName());
	}

	/**
	 * ���ͣ����þ�̬���������÷�������
	 */
	public static String upperFirst(String origin) {
		String upperCase = String.valueOf(origin.charAt(0)).toUpperCase();
		return upperCase + origin.substring(1);
	}

	@org.junit.Test
	public void type() throws Exception {
		ExpressionParser parser = new SpelExpressionParser();
		// �������T������ΪMath�Ǹ����������
		Integer value = parser.parseExpression("T(Math).min(3,4)").getValue(Integer.class);

		// ���÷�������
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("myMethod", this.getClass().getDeclaredMethod("upperFirst", String.class));
		Object upper = parser.parseExpression("#myMethod('this')").getValue(context);

		assertEquals((int) value, 3);
		assertEquals(upper, "This");
	}

	/**
	 * ���ñ���
	 */
	@org.junit.Test
	public void variable() {
		ExpressionParser parser = new SpelExpressionParser();
		// �������T������ΪMath�Ǹ����������
		Inventor value = new Inventor();
		EvaluationContext context = new StandardEvaluationContext();

		context.setVariable("name", "jerry");
		context.setVariable("arr", Arrays.asList(1, 2, 3, 4, 5, 6, 7));
		parser.parseExpression("Name = #name").getValue(context, value);
		// #root�ʹ��������
		Object root = parser.parseExpression("#root").getValue(context, value);

		// #this���Ż����ı仯���仯
		List list = parser.parseExpression("#arr.?[#this>6]").getValue(context, List.class);

		assertEquals("jerry", value.getName());
		assertEquals(root, value);
		assertEquals(list.size(), 1);
	}

	/**
	 * ��Ԫ���ʽ
	 */
	@org.junit.Test
	public void sanYuan() {
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("value", null);
		String value = parser.parseExpression("#value?:'no value'").getValue(context, String.class);
		assertEquals(value, "no value");
		context.setVariable("value", "value");
		value = parser.parseExpression("#value?:'no value'").getValue(context, String.class);
		assertEquals("value", "value");

	}

	/**
	 * ��ȫ������
	 */
	@org.junit.Test
	public void safe() {
		ExpressionParser parser = new SpelExpressionParser();
		Inventor vInventor = new Inventor();

		EvaluationContext context = new StandardEvaluationContext();
		Object value = parser.parseExpression("#root?.placeOfBirth?.city").getValue(vInventor);
		assertNull(value);
		PlaceOfBirth placeOfBirth = new PlaceOfBirth("wu xi");
		vInventor.setPlaceOfBirth(placeOfBirth);
		value = parser.parseExpression("#root?.placeOfBirth.city").getValue(vInventor);

		assertEquals(value, "wu xi");

	}

	/**
	 * ����ѡ��
	 */
	@org.junit.Test
	public void select() {
		ExpressionParser parser = new SpelExpressionParser();

		EvaluationContext context = new StandardEvaluationContext();
		Society society = new Society();
		List value = parser.parseExpression("members?.?[name.startsWith('j')]").getValue(society, List.class);
		assertEquals(value.size(), 1);

		Inventor first = parser.parseExpression("members?.^[true]").getValue(society, Inventor.class);
		Inventor last = parser.parseExpression("members?.$[name=='s']").getValue(society, Inventor.class);

		List strs = parser.parseExpression("members?.![name]").getValue(society, List.class);
		assertNull(last);
		assertEquals(first.getName(), "mike");
		assertTrue(strs.get(0) instanceof String);
	}

}
