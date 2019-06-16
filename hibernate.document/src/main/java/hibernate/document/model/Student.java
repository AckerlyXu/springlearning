package hibernate.document.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Formula;

@Entity
//DynamicInsert的作用是插入的时候只会插入不为空的值
@DynamicInsert
@NamedQuery(name = "showStudent", query = "select s from Student s where s.age>:age")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ColumnDefault("'N/A'")
	private String name;
	@ColumnDefault("0")
	private Integer age;
	@Formula(value = "age+1")
	private Integer agePlus;

	private String name2;

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Temporal(TemporalType.DATE)
	private Date date;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", date=" + date + "]";
	}

}
