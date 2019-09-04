package hello;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Dto {

    @Id
    private String id;

	public Dto() {
	}

	public Dto(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Dto dto = (Dto) o;
		return Objects.equals(getId(), dto.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Dto.class.getSimpleName() + "[", "]")
				.add("id='" + id + "'")
				.toString();
	}
}

