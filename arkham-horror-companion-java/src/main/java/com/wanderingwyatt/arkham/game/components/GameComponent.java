package com.wanderingwyatt.arkham.game.components;

import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class GameComponent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Version
	private Integer version;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="expansion_id", referencedColumnName = "id", nullable = false)
	protected Expansion expansion;

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
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
