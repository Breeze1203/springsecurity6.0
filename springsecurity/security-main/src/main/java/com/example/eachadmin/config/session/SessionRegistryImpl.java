package com.example.eachadmin.config.session;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionRegistryImpl implements SessionRegistry {
    private final ConcurrentMap<Object, Set<String>> principals;
    private final Map<String, SessionInformation> sessionIds;

    public SessionRegistryImpl() {
        this.principals = new ConcurrentHashMap<>();
        this.sessionIds = new ConcurrentHashMap<>();
    }

    @Override
    public List<Object> getAllPrincipals() {
        return new ArrayList<>(this.principals.keySet());
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        Set<String> sessionIdsForPrincipal = this.principals.get(principal);
        if (sessionIdsForPrincipal == null) {
            return Collections.emptyList();
        }
        List<SessionInformation> sessions = new ArrayList<>(sessionIdsForPrincipal.size());
        for (String sessionId : sessionIdsForPrincipal) {
            SessionInformation sessionInformation = this.sessionIds.get(sessionId);
            if (sessionInformation == null || (sessionInformation.isExpired() && !includeExpiredSessions)) {
                continue;
            }
            sessions.add(sessionInformation);
        }
        return sessions;
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        return this.sessionIds.get(sessionId);
    }

    @Override
    public void refreshLastRequest(String sessionId) {
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            info.refreshLastRequest();
        }
    }

    @Override
    public void registerNewSession(String sessionId, Object principal) {
        if (this.sessionIds.containsKey(sessionId)) {
            return;
        }
        this.sessionIds.put(sessionId, new SessionInformation(principal, sessionId, new Date()));
        Set<String> sessions = this.principals.computeIfAbsent(principal, key -> ConcurrentHashMap.newKeySet());
        sessions.add(sessionId);
    }

    @Override
    public void removeSessionInformation(String sessionId) {
        SessionInformation info = this.sessionIds.get(sessionId);
        if (info != null) {
            this.sessionIds.remove(sessionId);
            Set<String> sessions = this.principals.get(info.getPrincipal());
            if (sessions != null) {
                sessions.remove(sessionId);
                if (sessions.isEmpty()) {
                    this.principals.remove(info.getPrincipal());
                }
            }
        }
    }
}
