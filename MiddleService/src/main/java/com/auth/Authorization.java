package com.auth;

import javax.servlet.http.HttpServletRequest;

public class Authorization {

    public static boolean isAutorized(final HttpServletRequest request,
                                      final String src) {
        return true;
    }

    public static int authorizedUser(final HttpServletRequest request,
                                     final String src) {
        return 1;
    }


}
