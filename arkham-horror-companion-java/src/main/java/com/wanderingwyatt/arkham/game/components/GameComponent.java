package com.wanderingwyatt.arkham.game.components;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class GameComponent implements Identifiable {
	@Id
	@GeneratedValue
	protected UUID id;
	
	@Version
	private Integer version;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="expansion_id", referencedColumnName = "id", nullable = false)
	protected Expansion expansion;

	public void setId(UUID id) {
		this.id = id;
	}
	
	@Override
	public UUID getId() {
		return id;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setExpansion(Expansion expansion) {
		this.expansion = expansion;
	}
	
	public Expansion getExpansion() {
		return expansion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(expansion, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameComponent other = (GameComponent) obj;
		return Objects.equals(expansion, other.expansion) && Objects.equals(id, other.id);
	}
}
