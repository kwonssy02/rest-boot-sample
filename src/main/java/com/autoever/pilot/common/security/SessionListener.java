package com.autoever.pilot.common.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(60 * 30); // 30분 session timeout
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
