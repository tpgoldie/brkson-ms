package com.tpg.brks.ms.expenses.service.clients;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class AuthenticatedUserMatcher extends TypeSafeDiagnosingMatcher<AuthenticatedUser> {

    public static AuthenticatedUserMatcher matchesAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        return new AuthenticatedUserMatcher(authenticatedUser);
    }

    private AuthenticatedUser expected;

    public AuthenticatedUserMatcher(AuthenticatedUser expected) {

        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(AuthenticatedUser value, Description description) {
        return expected.equals(value);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("authenticated user matches %s", expected.getUsername()));
    }
}
