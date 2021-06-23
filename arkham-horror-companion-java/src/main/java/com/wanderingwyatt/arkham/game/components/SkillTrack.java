package com.wanderingwyatt.arkham.game.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

public class SkillTrack {
	private List<Integer> speed = new ArrayList<>();
	private List<Integer> sneak = new ArrayList<>();
	private List<Integer> fight = new ArrayList<>();
	private List<Integer> will = new ArrayList<>();
	private List<Integer> lore = new ArrayList<>();
	private List<Integer> luck = new ArrayList<>();
	
	protected SkillTrack() {
		// For jackson-databind
	}
	
	public List<Integer> getSpeed() {
		return speed;
	}

	public void setSpeed(List<Integer> speed) {
		this.speed = speed;
	}

	public List<Integer> getSneak() {
		return sneak;
	}

	public void setSneak(List<Integer> sneak) {
		this.sneak = sneak;
	}

	public List<Integer> getFight() {
		return fight;
	}

	public void setFight(List<Integer> fight) {
		this.fight = fight;
	}

	public List<Integer> getWill() {
		return will;
	}

	public void setWill(List<Integer> will) {
		this.will = will;
	}

	public List<Integer> getLore() {
		return lore;
	}

	public void setLore(List<Integer> lore) {
		this.lore = lore;
	}

	public List<Integer> getLuck() {
		return luck;
	}

	public void setLuck(List<Integer> luck) {
		this.luck = luck;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fight, lore, luck, sneak, speed, will);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkillTrack other = (SkillTrack) obj;
		return Objects.equals(fight, other.fight) && Objects.equals(lore, other.lore)
				&& Objects.equals(luck, other.luck) && Objects.equals(sneak, other.sneak)
				&& Objects.equals(speed, other.speed) && Objects.equals(will, other.will);
	}

	@Generated("SparkTools")
	private SkillTrack(Builder builder) {
		this.speed = builder.speed;
		this.sneak = builder.sneak;
		this.fight = builder.fight;
		this.will = builder.will;
		this.lore = builder.lore;
		this.luck = builder.luck;
	}
	
	/**
	 * Creates builder to build {@link SkillTrack}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	
	/**
	 * Builder to build {@link SkillTrack}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private List<Integer> speed = Collections.emptyList();
		private List<Integer> sneak = Collections.emptyList();
		private List<Integer> fight = Collections.emptyList();
		private List<Integer> will = Collections.emptyList();
		private List<Integer> lore = Collections.emptyList();
		private List<Integer> luck = Collections.emptyList();

		private Builder() {
		}

		public Builder withSpeed(List<Integer> speed) {
			this.speed = speed;
			return this;
		}

		public Builder withSneak(List<Integer> sneak) {
			this.sneak = sneak;
			return this;
		}

		public Builder withFight(List<Integer> fight) {
			this.fight = fight;
			return this;
		}

		public Builder withWill(List<Integer> will) {
			this.will = will;
			return this;
		}

		public Builder withLore(List<Integer> lore) {
			this.lore = lore;
			return this;
		}

		public Builder withLuck(List<Integer> luck) {
			this.luck = luck;
			return this;
		}

		public SkillTrack build() {
			return new SkillTrack(this);
		}
	}
}
