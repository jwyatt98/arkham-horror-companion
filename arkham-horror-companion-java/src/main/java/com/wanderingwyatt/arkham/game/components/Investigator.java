package com.wanderingwyatt.arkham.game.components;

import java.util.Objects;
import javax.annotation.Generated;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "investigator-cache")
public class Investigator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true)
	private String name;
	private Integer health;
	private Integer sanity;

	@Generated("SparkTools")
	private Investigator(Builder builder) {
		this.name = builder.name;
		this.health = builder.health;
		this.sanity = builder.sanity;
	}
	
	protected Investigator() {
		// protected default constructor
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getHealth() {
		return health;
	}
	
	public void setHealth(Integer health) {
		this.health = health;
	}
	
	public Integer getSanity() {
		return sanity;
	}
	
	public void setSanity(Integer sanity) {
		this.sanity = sanity;
	}
	
	public Integer getId() {
		return id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((health == null) ? 0 : health.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sanity == null) ? 0 : sanity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Investigator other = (Investigator) obj;
		return Objects.equals(id, other.id) 
				&&  Objects.equals(health, other.health)
				&& Objects.equals(sanity, other.sanity)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Investigator [id=" + id + ", name=" + name + ", health=" + health + ", sanity=" + sanity + "]";
	}

	/**
	 * Creates builder to build {@link Investigator}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Investigator}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String name;
		private Integer health;
		private Integer sanity;

		private Builder() {
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withHealth(Integer health) {
			this.health = health;
			return this;
		}

		public Builder withSanity(Integer sanity) {
			this.sanity = sanity;
			return this;
		}

		public Investigator build() {
			return new Investigator(this);
		}
	}
}
