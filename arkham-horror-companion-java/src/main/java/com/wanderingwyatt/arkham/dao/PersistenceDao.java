package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.game.components.Identifiable;
import jakarta.persistence.EntityGraph;
import java.util.Optional;
import java.util.UUID;

public interface PersistenceDao {

	String JAVAX_PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";
	
	/**
	 * Persists the entity <tt>T</tt> to the underlying data store.
	 * @param t The entity to persist.
	 * @throws ArkhamHorrorDaoException if the operation fails for any reason.
	 */
	<T extends Identifiable> void persist(T t) throws ArkhamHorrorDaoException;
	
	/**
	 * Updates the entity <tt>T</tt> in the underlying data store.
	 * @param t The entity to update.
	 * @return The updated entity.
	 * @throws ArkhamHorrorDaoException if the operation fails for any reason.
	 */
	<T extends Identifiable> T merge(T t) throws ArkhamHorrorDaoException;
	
	/**
	 * Removes the entity <tt>T</tt> from the underlying data store.
	 * @param t The entity to remove.
	 * @throws ArkhamHorrorDaoException if the operation fails for any reason.
	 */
	<T extends Identifiable> void remove(T t) throws ArkhamHorrorDaoException;
	
	/**
	 * Attempts to find and return the entity represented by <tt>id</tt>. If the entity could not be found then an empty {@linkplain Optional} is returned.
	 * @param clazz The {@linkplain Class} that represents the entity with the specified id.
	 * @param id The {@linkplain UUID} that represents an entity in the underlying data store.
	 * @return The entity from the data store if it exists, otherwise an empty {@linkplain Optional}.
	 * @throws ArkhamHorrorDaoException if the operation fails for any reason.
	 */
	<T extends Identifiable> Optional<T> find(Class<T> clazz, UUID id) throws ArkhamHorrorDaoException;
	
	/**
	 * Attempts to find the entity in the underlying data store and load it according to the provided {@linkplain EntityGraphProvider}.
	 * @param type The {@linkplain Class} that represents the entity with the specified id.
	 * @param id The {@linkplain UUID} that represents an entity in the underlying data store.
	 * @param egp The {@linkplain EntityGraphProvider} that provides that attributes that will be loaded.
	 * @return  The entity from the data store if it exists, otherwise an empty {@linkplain Optional}.
	 */
	<T extends Identifiable> Optional<T> findByEntityGraph(Class<T> type, UUID id, EntityGraphProvider egp) throws ArkhamHorrorDaoException;
	
	@FunctionalInterface
	interface EntityGraphProvider {
		void addAttributeNodes(EntityGraph<?> entityGraph);
	}
}
