package pl.trytek.easytrip.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "atrakcje", schema = "public")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Attraction implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "nazwa")
	private String name;

	@Column(name = "opis")
	private String description;

	@Column(name = "kod_pocztowy")
	private String postalCode;

	@Column(name = "miejscowosc")
	private String city;

	@Column(name = "ulica")
	private String street;

	@Column(name = "nr_budynku")
	private String buildingNumber;

	@Column(name = "nr_lokalu")
	private String apartmentNumber;

	@Column(name="nr_telefonu")
	private String phoneNumber;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Attraction attraction = (Attraction) o;
		return id != null && Objects.equals(id, attraction.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
