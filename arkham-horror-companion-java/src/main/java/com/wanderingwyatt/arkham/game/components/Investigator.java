package com.wanderingwyatt.arkham.game.components;

import java.util.Objects;
import javax.annotation.Generated;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.wanderingwyatt.arkham.annotations.cache.CacheConfiguration;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "investigator-cache")
@CacheConfiguration(cacheName = "investigator-cache", key = Integer.class)
public class Investigator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true)
	private String name;
	private String title;
	private Integer health;
	private Integer sanity;
	private Integer focus;
	private String home;
	
	@Convert(converter = SkillTrackConverter.class)
	private SkillTrack skillTrack;

	@Generated("SparkTools")
	private Investigator(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.title = builder.title;
		this.health = builder.health;
		this.sanity = builder.sanity;
		this.focus = builder.focus;
		this.home = builder.home;
		this.skillTrack = builder.skillTrack;
	}

	protected Investigator() {
		// protected default constructor
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public Integer getHealth() {
		return health;
	}

	public Integer getSanity() {
		return sanity;
	}

	public Integer getFocus() {
		return focus;
	}

	public String getHome() {
		return home;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}

	public void setSanity(Integer sanity) {
		this.sanity = sanity;
	}

	public void setFocus(Integer focus) {
		this.focus = focus;
	}

	public void setHome(String home) {
		this.home = home;
	}
	
	@Override
	public String toString() {
		return "Investigator [id=" + id + ", name=" + name + ", title=" + title + ", health=" + health + ", sanity="
				+ sanity + ", focus=" + focus + ", home=" + home + ", skillTrack=" + skillTrack + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(focus, health, home, id, name, sanity, skillTrack, title);
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
		return Objects.equals(focus, other.focus) && Objects.equals(health, other.health)
				&& Objects.equals(home, other.home) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(sanity, other.sanity) && Objects.equals(skillTrack, other.skillTrack)
				&& Objects.equals(title, other.title);
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
		private Integer id;
		private String name;
		private String title;
		private Integer health;
		private Integer sanity;
		private Integer focus;
		private String home;
		private SkillTrack skillTrack;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withTitle(String title) {
			this.title = title;
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

		public Builder withFocus(Integer focus) {
			this.focus = focus;
			return this;
		}

		public Builder withHome(String home) {
			this.home = home;
			return this;
		}

		public Builder withSkillTrack(SkillTrack skillTrack) {
			this.skillTrack = skillTrack;
			return this;
		}

		public Investigator build() {
			return new Investigator(this);
		}
	}
}
