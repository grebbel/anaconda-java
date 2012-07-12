package nl.runnable.lims.domain;

import java.util.Set;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {

	public static volatile SingularAttribute<User, String> email;

	public static volatile SingularAttribute<User, Password> password;

	public static volatile PluralAttribute<User, Set<Role>, Role> roles;

	public static volatile SingularAttribute<User, PersonName> name;
}
