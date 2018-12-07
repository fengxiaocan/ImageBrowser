package com.evil.imagebrowser.network.cookie;

import java.io.Serializable;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import static okhttp3.internal.Util.verifyAsIpAddress;

public class CookieInfo implements Serializable {
    private String name;
    private String value;
    private long expiresAt;
    private String domain;
    private String path;
    private boolean secure;
    private boolean httpOnly;

    private boolean persistent;
    private boolean hostOnly;

    private String cookieKey;
    @Deprecated
    private String fileName;

    public CookieInfo() {
    }

    public CookieInfo(Cookie cookie) {
        this.name = checkNull(cookie.name());
        this.value = checkNull(cookie.value());
        this.expiresAt = cookie.expiresAt();
        this.domain = checkNull(cookie.domain());
        this.path = checkNull(cookie.path());
        this.secure = cookie.secure();
        this.httpOnly = cookie.httpOnly();
        this.persistent = cookie.persistent();
        this.hostOnly = cookie.hostOnly();

        cookieKey = createCookieKey(cookie);
        fileName = "cookie_" + this.hashCode() + ".cookie";
    }

    public static String createCookieKey(Cookie cookie) {
        return (cookie.secure() ? "https" : "http") +
               "://" +
               cookie.domain() +
               cookie.path() +
               "|" +
               cookie.name();
    }

    private static boolean domainMatch(String urlHost,String domain) {
        if (urlHost.equals(domain)) {
            return true;
        }

        if (urlHost.endsWith(domain) &&
            urlHost.charAt(urlHost.length() - domain.length() - 1) == '.' &&
            !verifyAsIpAddress(urlHost))
        {
            return true; // As in 'example.com' matching 'www.example.com'.
        }

        return false;
    }

    private static boolean pathMatch(HttpUrl url,String path) {
        String urlPath = url.encodedPath();
        if (urlPath.equals(path)) {
            return true; // As in '/foo' matching '/foo'.
        }

        if (urlPath.startsWith(path)) {
            if (path.endsWith("/")) {
                return true; // As in '/' matching '/foo'.
            }
            if (urlPath.charAt(path.length()) == '/') {
                return true; // As in '/foo' matching '/foo/bar'.
            }
        }

        return false;
    }

    private String checkNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
    @Deprecated
    public String getFileName() {
        return fileName;
    }
    @Deprecated
    public void setFileName(String fileName) {
        this.fileName = checkNull(fileName);
    }

    public void setCookieInfo(Cookie cookie) {
        this.name = checkNull(cookie.name());
        this.value = checkNull(cookie.value());
        this.expiresAt = cookie.expiresAt();
        this.domain = checkNull(cookie.domain());
        this.path = checkNull(cookie.path());
        this.secure = cookie.secure();
        this.httpOnly = cookie.httpOnly();
        this.persistent = cookie.persistent();
        this.hostOnly = cookie.hostOnly();
        cookieKey = createCookieKey(cookie);
        fileName = "cookie_" + this.hashCode() + ".cookie";
    }

    public boolean matches(HttpUrl url) {
        boolean domainMatch = hostOnly ? url.host().equals(domain) : domainMatch(url.host(),domain);
        if (!domainMatch) {
            return false;
        }

        if (!pathMatch(url,path)) {
            return false;
        }

        if (secure && !url.isHttps()) {
            return false;
        }

        return true;
    }

    public String getCookieKey() {
        return cookieKey;
    }

    public void setCookieKey(String cookieKey) {
        this.cookieKey = checkNull(cookieKey);
        fileName = "cookie_" + this.hashCode() + ".cookie";
    }

    public Cookie toCookie() {
        Cookie.Builder cookieBuilder = new Cookie.Builder().name(name)
                                                           .value(value)
                                                           .expiresAt(expiresAt);
        if (httpOnly) {
            cookieBuilder.httpOnly();
        }
        if (hostOnly) {
            cookieBuilder.hostOnlyDomain(domain);
        } else {
            cookieBuilder.domain(domain);
        }
        if (secure) {
            cookieBuilder.secure();
        }
        return cookieBuilder.build();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = checkNull(name);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = checkNull(value);
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public boolean isHostOnly() {
        return hostOnly;
    }

    public void setHostOnly(boolean hostOnly) {
        this.hostOnly = hostOnly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CookieInfo)) {
            return false;
        }

        CookieInfo that = (CookieInfo)o;

        if (getExpiresAt() != that.getExpiresAt()) {
            return false;
        }
        if (isSecure() != that.isSecure()) {
            return false;
        }
        if (isHttpOnly() != that.isHttpOnly()) {
            return false;
        }
        if (isPersistent() != that.isPersistent()) {
            return false;
        }
        if (isHostOnly() != that.isHostOnly()) {
            return false;
        }
        if (getName() == null) {
            if (that.getName() != null) {
                return false;
            }
        } else if (!getName().equals(that.getName())) {
            return false;
        }
        if (getValue() == null) {
            if (that.getValue() != null) {
                return false;
            }
        } else if (!getValue().equals(that.getValue())) {
            return false;
        }
        if (getDomain() == null) {
            if (that.getDomain() != null) {
                return false;
            }
        } else if (!getDomain().equals(that.getDomain())) {
            return false;
        }
        if (getPath() == null) {
            if (that.getPath() != null) {
                return false;
            } else {
                return true;
            }
        } else {
            return getPath().equals(that.getPath());
        }
    }

    @Override
    public int hashCode() {
        int result = getHashCode(getName());
        result = 31 * result + getHashCode(getValue());
        result = 31 * result + (int)(getExpiresAt() ^ (getExpiresAt() >>> 32));
        result = 31 * result + getHashCode(getDomain());
        result = 31 * result + getHashCode(getPath());
        result = 31 * result + (isSecure() ? 1 : 0);
        result = 31 * result + (isHttpOnly() ? 1 : 0);
        result = 31 * result + (isPersistent() ? 1 : 0);
        result = 31 * result + (isHostOnly() ? 1 : 0);
        return result;
    }

    int getHashCode(String value) {
        if (value == null) {
            return 0;
        }
        return value.hashCode();
    }
}
