package springframework.doc.convert;

import java.beans.PropertyEditorSupport;

public class ExoticTypeEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		    ExoticType exoticType = new ExoticType();
		    exoticType.setName(text.toUpperCase());
		    setValue(exoticType);
	}
	

}
