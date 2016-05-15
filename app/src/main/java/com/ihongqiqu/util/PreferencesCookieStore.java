/**
 * Copyright 2014 Zhenguo Jin
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ihongqiqu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A CookieStore impl, it's save cookie to SharedPreferences.
 *
 * @author jingle1267@163.com
 */
public class PreferencesCookieStore implements CookieStore {

    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    private final ConcurrentHashMap<String, Cookie> cookies;
    private final SharedPreferences cookiePrefs;

    /**
     * Construct a persistent cookie store.
     */
    public PreferencesCookieStore(Context context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS,
                Context.MODE_PRIVATE);
        cookies = new ConcurrentHashMap<String, Cookie>();

        // Load any previously stored cookies into the store
        String storedCookieNames = cookiePrefs.getString(COOKIE_NAME_STORE,
                null);
        if (storedCookieNames != null) {
            String[] cookieNames = TextUtils.split(storedCookieNames, ",");
            for (String name : cookieNames) {
                String encodedCookie = cookiePrefs.getString(COOKIE_NAME_PREFIX
                        + name, null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        cookies.put(name, decodedCookie);
                    }
                }
            }

            // Clear out expired cookies
            clearExpired(new Date());
        }
    }

    @Override
    public void addCookie(Cookie cookie) {
        String name = cookie.getName();

        // Save cookie into local store, or remove if expired
        if (!cookie.isExpired(new Date())) {
            cookies.put(name, cookie);
        } else {
            cookies.remove(name);
        }

        // Save cookie into persistent store
        SharedPreferences.Editor editor = cookiePrefs.edit();
        editor.putString(COOKIE_NAME_STORE,
                TextUtils.join(",", cookies.keySet()));
        editor.putString(COOKIE_NAME_PREFIX + name,
                encodeCookie(new SerializableCookie(cookie)));
        editor.commit();
    }

    @Override
    public void clear() {
        // Clear cookies from persistent store
        SharedPreferences.Editor editor = cookiePrefs.edit();
        for (String name : cookies.keySet()) {
            editor.remove(COOKIE_NAME_PREFIX + name);
        }
        editor.remove(COOKIE_NAME_STORE);
        editor.commit();

        // Clear cookies from local store
        cookies.clear();
    }

    @Override
    public boolean clearExpired(Date date) {
        boolean clearedAny = false;
        SharedPreferences.Editor editor = cookiePrefs.edit();

        for (ConcurrentHashMap.Entry<String, Cookie> entry : cookies.entrySet()) {
            String name = entry.getKey();
            Cookie cookie = entry.getValue();
            if (cookie.getExpiryDate() == null || cookie.isExpired(date)) {
                // Remove the cookie by name
                cookies.remove(name);

                // Clear cookies from persistent store
                editor.remove(COOKIE_NAME_PREFIX + name);

                // We've cleared at least one
                clearedAny = true;
            }
        }

        // Update names in persistent store
        if (clearedAny) {
            editor.putString(COOKIE_NAME_STORE,
                    TextUtils.join(",", cookies.keySet()));
        }
        editor.commit();

        return clearedAny;
    }

    @Override
    public List<Cookie> getCookies() {
        return new ArrayList<Cookie>(cookies.values());
    }

    public Cookie getCookie(String name) {
        return cookies.get(name);
    }

    protected String encodeCookie(SerializableCookie cookie) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (Throwable e) {
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    protected Cookie decodeCookie(String cookieStr) {
        byte[] bytes = hexStringToByteArray(cookieStr);
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(is);
            cookie = ((SerializableCookie) ois.readObject()).getCookie();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return cookie;
    }

    // Using some super basic byte array <-> hex conversions so we don't have
    // to rely on any large Base64 libraries. Can be overridden if you like!
    protected String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (byte element : b) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    protected byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public class SerializableCookie implements Serializable {
        private static final long serialVersionUID = 6374381828722046732L;

        private transient final Cookie cookie;
        private transient BasicClientCookie clientCookie;

        public SerializableCookie(Cookie cookie) {
            this.cookie = cookie;
        }

        public Cookie getCookie() {
            Cookie bestCookie = cookie;
            if (clientCookie != null) {
                bestCookie = clientCookie;
            }
            return bestCookie;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(cookie.getName());
            out.writeObject(cookie.getValue());
            out.writeObject(cookie.getComment());
            out.writeObject(cookie.getDomain());
            out.writeObject(cookie.getExpiryDate());
            out.writeObject(cookie.getPath());
            out.writeInt(cookie.getVersion());
            out.writeBoolean(cookie.isSecure());
        }

        private void readObject(ObjectInputStream in) throws IOException,
                ClassNotFoundException {
            String name = (String) in.readObject();
            String value = (String) in.readObject();
            clientCookie = new BasicClientCookie(name, value);
            clientCookie.setComment((String) in.readObject());
            clientCookie.setDomain((String) in.readObject());
            clientCookie.setExpiryDate((Date) in.readObject());
            clientCookie.setPath((String) in.readObject());
            clientCookie.setVersion(in.readInt());
            clientCookie.setSecure(in.readBoolean());
        }
    }
}