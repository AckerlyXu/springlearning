package hibernate.document.model.collection.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;

/**
 * 自定义hibernate类型，把List<String>转换成逗号分隔的varchar
 * 
 * @author xuqiang
 *
 */
public class CommaDelimitedStringsJavaTypeDescriptor extends AbstractTypeDescriptor<List> {

	public CommaDelimitedStringsJavaTypeDescriptor() {

		super(List.class, new MutableMutabilityPlan<List>() {

			@Override
			protected List deepCopyNotNull(List value) {
				// TODO Auto-generated method stub
				return new ArrayList<>(value);
			}
		});

	}

	@Override
	public List<String> fromString(String string) {
		// TODO Auto-generated method stub
		return Arrays.asList((string.split(",")));
	}

	@Override
	public String toString(List value) {
		// TODO Auto-generated method stub
		return ((List<String>) value).stream().collect(Collectors.joining(","));
	}

	@Override
	public <X> X unwrap(List value, Class<X> type, WrapperOptions options) {
		// TODO Auto-generated method stub
		return (X) toString(value);

	}

	@Override
	public <X> List<String> wrap(X value, WrapperOptions options) {
		// TODO Auto-generated method stub

		return fromString((String) value);
	}

}
