package com.wanderingwyatt.arkham.game.components;

import com.wanderingwyatt.arkham.annotations.cache.CacheConfiguration;
import com.wanderingwyatt.arkham.dao.converter.SkillTrackConverter;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityGraph;
import java.util.Objects;
import javax.annotation.Generated;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "investigator-cache")
@CacheConfiguration(cacheName = "investigator-cache", key = Integer.class)
public class Investigator extends GameComponent {
	
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
		this.expansion = builder.expansion;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(focus, health, home, name, sanity, skillTrack, title);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Investigator other = (Investigator) obj;
		return Objects.equals(focus, other.focus) && Objects.equals(health, other.health)
				&& Objects.equals(home, other.home) && Objects.equals(name, other.name)
				&& Objects.equals(sanity, other.sanity) && Objects.equals(skillTrack, other.skillTrack)
				&& Objects.equals(title, other.title);
	}
	
	@Override
	public String toString() {
		return "Investigator [name=" + name + ", title=" + title + ", health=" + health + ", sanity=" + sanity
				+ ", focus=" + focus + ", home=" + home + ", skillTrack=" + skillTrack + ", id=" + id + "]";
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
		private Expansion expansion;
		private String name;
		private String title;
		private Integer health;
		private Integer sanity;
		private Integer focus;
		private String home;
		private SkillTrack skillTrack;

		private Builder() {
		}

		public Builder withExpansion(Expansion expansion) {
			this.expansion = expansion;
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
	
	public static void addAttributeNodes(EntityGraph<?> entityGraph) {
		entityGraph.addAttributeNodes("name", "title", "health", "sanity", "focus", "home", "skillTrack", "expansion");
	}
}
