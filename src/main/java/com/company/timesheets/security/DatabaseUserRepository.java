package com.company.timesheets.security;

import com.company.timesheets.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import io.jmix.securitydata.user.AbstractDatabaseUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Primary
@Component("ts_UserRepository")
public class DatabaseUserRepository extends AbstractDatabaseUserRepository<User> {

    private final DataManager dataManager1;

    public DatabaseUserRepository(DataManager dataManager1) {
        this.dataManager1 = dataManager1;
    }

    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void initSystemUser(final User systemUser) {
        final Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                .addResourceRole(FullAccessRole.CODE)
                .build();
        systemUser.setAuthorities(authorities);

    }

    @Override
    protected void initAnonymousUser(final User anonymousUser) {
    }
}