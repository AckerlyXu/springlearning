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
	 * SpEl Hello World 调用一个方法
	 */
	@org.junit.Test
	public void spELHelloWorld() {

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("'Hello World'.concat('!')");
		String msg = (String) express.getValue();
		System.out.println(msg);
	}

	/**
	 * 获得属性
	 */
	@org.junit.Test
	public void spELGetProperty() {

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("'Hello World'.bytes");
		byte[] msg = (byte[]) express.getValue();
		System.out.println(msg.length);
	}

	/**
	 * 获得嵌套的属性,以及调用构造函数创建对象
	 */
	@org.junit.Test
	public void spELGetNestedProperty() {

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("new String('Hello World').bytes.length");
		int msg = express.getValue(Integer.class);// 如果向getValue中传递了参数，那么就不需要强转了
		System.out.println(msg);
	}

	/**
	 * 设置rootObject
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
	 * 配置 parser
	 */
	@org.junit.Test
	public void parserConfiguration() {

		Demo demo = new Demo();
		// 第三个参数代表遇到null值的时候是否自动创建对象
		// 第四个参数代表角标越界的时候是否自动向集合中添加元素
		SpelParserConfiguration configuration = new SpelParserConfiguration(SpelCompilerMode.MIXED, null, true, true,
				Integer.MAX_VALUE);

		ExpressionParser parser = new SpelExpressionParser(configuration);
		Expression express = parser.parseExpression("list[5]");
		express.getValue(demo, String.class);

		Assert.assertEquals(6, demo.list.size());
	}

	/**
	 * 在xml中使用spel表达式
	 */
	@org.junit.Test
	public void spelAndApplicationContext() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spel.xml");
		ThingTwo bean = context.getBean(ThingTwo.class);
		Inventor bean2 = context.getBean(Inventor.class);
		assertEquals(bean.getInfo(), bean2.getName());

	}

	/**
	 * 配置 inLine collection
	 */
	@org.junit.Test
	public void collection() {

		ExpressionParser parser = new SpelExpressionParser();
		Expression express = parser.parseExpression("{1,2,3}");
		// 为了性能，同一个parser，parse相同的字符串，会返回相同的list
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
	 * 赋值
	 */
	@org.junit.Test
	public void assign() {
		ExpressionParser parser = new SpelExpressionParser();
		Inventor vInventor = new Inventor();
		// 第一种
		Object obj = parser.parseExpression("Name = 'mike'").getValue(vInventor);
		// 第二种
		parser.parseExpression("Name").setValue(vInventor, "jerry");
		System.out.println(vInventor.getName());
	}

	/**
	 * 类型，调用静态方法，调用方法变量
	 */
	public static String upperFirst(String origin) {
		String upperCase = String.valueOf(origin.charAt(0)).toUpperCase();
		return upperCase + origin.substring(1);
	}

	@org.junit.Test
	public void type() throws Exception {
		ExpressionParser parser = new SpelExpressionParser();
		// 如果不加T，会仍为Math是根对象的属性
		Integer value = parser.parseExpression("T(Math).min(3,4)").getValue(Integer.class);

		// 调用方法变量
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("myMethod", this.getClass().getDeclaredMethod("upperFirst", String.class));
		Object upper = parser.parseExpression("#myMethod('this')").getValue(context);

		assertEquals((int) value, 3);
		assertEquals(upper, "This");
	}

	/**
	 * 引用变量
	 */
	@org.junit.Test
	public void variable() {
		ExpressionParser parser = new SpelExpressionParser();
		// 如果不加T，会仍为Math是根对象的属性
		Inventor value = new Inventor();
		EvaluationContext context = new StandardEvaluationContext();

		context.setVariable("name", "jerry");
		context.setVariable("arr", Arrays.asList(1, 2, 3, 4, 5, 6, 7));
		parser.parseExpression("Name = #name").getValue(context, value);
		// #root就代表根对象
		Object root = parser.parseExpression("#root").getValue(context, value);

		// #this随着环境的变化而变化
		List list = parser.parseExpression("#arr.?[#this>6]").getValue(context, List.class);

		assertEquals("jerry", value.getName());
		assertEquals(root, value);
		assertEquals(list.size(), 1);
	}

	/**
	 * 三元表达式
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
	 * 安全导航符
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
	 * 集合选择
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
