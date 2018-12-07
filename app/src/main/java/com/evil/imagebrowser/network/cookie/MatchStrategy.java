package com.evil.imagebrowser.network.cookie;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by null on 07/12/2017.
 */

public interface MatchStrategy {
    boolean match(HttpUrl httpUrl,Cookie cookie);
}
