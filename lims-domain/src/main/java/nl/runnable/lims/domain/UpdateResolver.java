package nl.runnable.lims.domain;

/**
 * Strategy for resolving update classes against entity classes and obtaining {@link UpdateHandler}s.
 * 
 * @author Laurens Fridael
 * 
 */
public interface UpdateResolver {

	/**
	 * Obtains the update class for a given entity class.
	 * 
	 * @param entityClass
	 * @return The matching update class or null if none could be determined.
	 */
	public Class<?> getUpdateClass(Class<?> entityClass);

	public <UpdateType, EntityType> UpdateHandler<UpdateType, EntityType> getUpdateHandler(
			final Class<UpdateType> updateClass, final Class<EntityType> entityClass);

}
