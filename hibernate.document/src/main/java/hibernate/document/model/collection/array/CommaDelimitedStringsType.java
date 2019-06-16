package hibernate.document.model.collection.array;

import java.util.List;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class CommaDelimitedStringsType extends AbstractSingleColumnStandardBasicType<List> {
	public CommaDelimitedStringsType() {
		super(VarcharTypeDescriptor.INSTANCE, new CommaDelimitedStringsJavaTypeDescriptor());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "comma_delimited_strings";
	}

}
