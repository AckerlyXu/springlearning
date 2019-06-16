package springframework.doc.prop.primitive;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Required;

public class PureDatasource {
	  private String driverClassName;
	  private String url;
	  private String username;
	  private String password;
	  private Properties props;
	
	  private String extra="exture";
	  @Required
	  public void setExtra(String extra) {
		  this.extra = extra;
	  }
	public Properties getProps() {
		return props;
	}
	public void setProps(Properties props) {
		this.props = props;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "PureDatasource [driverClassName=" + driverClassName + ", url=" + url + ", username=" + username
				+ ", password=" + password + "]";
	}
	  

}
