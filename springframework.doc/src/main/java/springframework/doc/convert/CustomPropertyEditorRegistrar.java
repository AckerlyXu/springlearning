package springframework.doc.convert;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		// TODO Auto-generated method stub
		//如果需要ExoticType类型，就使用ExoticTypeEditor，它会把string转换成ExoticType类型
      registry.registerCustomEditor(ExoticType.class, new ExoticTypeEditor());
       
	}

}
