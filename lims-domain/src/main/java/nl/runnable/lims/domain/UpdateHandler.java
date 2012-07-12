package nl.runnable.lims.domain;

/**
 * Strategy for updating an entity using an value object representing the update.
 * 
 * @author Laurens Fridael
 * 
 * @param <UpdateType>
 * @param <EntityType>
 */
public interface UpdateHandler<UpdateType, EntityType> {

	void updateEntity(UpdateType update, EntityType entity);

}
