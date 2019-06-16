package spring.webmvc.model;

import com.fasterxml.jackson.annotation.JsonView;

public class Pet {
	public interface WithoutOwnerView {
	};

	public interface WithOwnerView extends WithoutOwnerView {
	};

	private int petId;
	private int ownerId;
	private String name;
	private int age;

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	@JsonView(WithOwnerView.class)
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@JsonView(WithoutOwnerView.class)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
