package pl.trytek.easytrip.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import pl.trytek.easytrip.data.converter.AttractionTypeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "atrakcja", schema = "easytrip")
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

	@ManyToOne()
	@JoinColumn(name="kraj_id")
	private Country country;

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

	@Column(name="czy_darmowe")
	private Boolean free;

	@Column(name = "typ")
	@Convert(converter = AttractionTypeConverter.class)
	private AttractionTypeEnum type;

	@OneToMany(mappedBy="attraction")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Like> likes;

	@OneToMany(mappedBy="attraction")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Favorite> favourites;

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
