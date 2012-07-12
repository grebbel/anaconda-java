-- http://static.springsource.org/spring-security/site/docs/3.1.x/reference/remember-me.html#remember-me-persistent-token
create table persistent_logins (username varchar(64) not null,
                                    series varchar(64) primary key,
                                    token varchar(64) not null,
                                    last_used timestamp not null)