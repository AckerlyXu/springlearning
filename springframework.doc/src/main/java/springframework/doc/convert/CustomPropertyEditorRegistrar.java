package springframework.doc.convert;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		// TODO Auto-generated method stub
		//�����ҪExoticType���ͣ���ʹ��ExoticTypeEditor�������stringת����ExoticType����
      registry.registerCustomEditor(ExoticType.class, new ExoticTypeEditor());
       
	}

}
