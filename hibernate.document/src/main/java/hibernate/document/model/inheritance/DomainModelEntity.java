package hibernate.document.model.inheritance;

public interface DomainModelEntity<ID> {

	ID getId();

	Integer getVersion();
}
