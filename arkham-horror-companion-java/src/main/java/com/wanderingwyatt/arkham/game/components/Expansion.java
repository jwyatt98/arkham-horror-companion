package com.wanderingwyatt.arkham.game.components;

import com.wanderingwyatt.arkham.annotations.cache.CacheConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityGraph;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "expansion-cache")
@CacheConfiguration(cacheName = "expansion-cache", key = Integer.class)
public class Expansion implements Identifiable {
	public static final String INVESTIGATORS_FIELD = "investigators";
	public static final String NAME_FIELD = "name";
	
	@Id
	@GeneratedValue
	protected UUID id;
	
	@Column(unique = true)
	private String name;
	
	@OneToMany(mappedBy = "expansion", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Investigator> investigators = new ArrayList<>();

	@Generated("SparkTools")
	private Expansion(Builder builder) {
		this.name = builder.name;
		this.investigators = builder.investigators;
	}

	public void addInvestigator(Investigator investigator) {
		investigators.add(investigator);
	}
	
	public void removeInvestigator(Investigator investigator) {
		investigators.remove(investigator);
	}
	
	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Investigator> getInvestigators() {
		return investigators;
	}

	protected Expansion() {
		// protected default constructor
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expansion other = (Expansion) obj;
		return Objects.equals(name, other.name);
	}

	/**
	 * Creates builder to build {@link Expansion}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Expansion}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String name;
		private List<Investigator> investigators = new ArrayList<>();

		private Builder() {
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withInvestigators(List<Investigator> investigators) {
			this.investigators.addAll(investigators);
			return this;
		}

		public Expansion build() {
			return new Expansion(this);
		}
	}
	
	public static void addAttributeNodes(EntityGraph<?> entityGraph) {
		entityGraph.addAttributeNodes(NAME_FIELD, INVESTIGATORS_FIELD);
	}
}
