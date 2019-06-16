/**
 * 
 */
/**
 * @author xuqiang
 *
 */
@org.hibernate.annotations.AnyMetaDef(name = "PropertyMetaDef", metaType = "string", idType = "integer", metaValues = {
		@org.hibernate.annotations.MetaValue(value = "str", targetEntity = hibernate.document.model.any.StringProperty.class),
		@org.hibernate.annotations.MetaValue(value = "integer", targetEntity = hibernate.document.model.any.IntegerProperty.class) })

package hibernate.document.model.any;