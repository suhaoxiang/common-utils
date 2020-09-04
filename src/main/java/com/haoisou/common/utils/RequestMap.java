package com.haoisou.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class RequestMap {
    private static final long serialVersionUID = 1L;
    Map map = null;
    HttpServletRequest request;

    public RequestMap(HttpServletRequest request) {
        this.request = request;
        Map returnMap = new HashMap();
        Map properties = request.getParameterMap();
        Iterator entries = properties.entrySet().iterator();
        String name = "";

        for(String value = ""; entries.hasNext(); returnMap.put(name, value)) {
            Map.Entry entry = (Map.Entry)entries.next();
            name = (String)entry.getKey();
            Object valueObj = entry.getValue();
            if (Tools.isEmpty(valueObj)) {
                value = "";
            } else if (!(valueObj instanceof String[])) {
                value = valueObj.toString();
            } else {
                String[] values = (String[])((String[])valueObj);

                for(int i = 0; i < values.length; ++i) {
                    value = values[i] + ",";
                }

                value = value.substring(0, value.length() - 1);
            }
        }

        this.map = returnMap;
    }

    public RequestMap() {
        this.map = new HashMap();
    }

    public Object get(Object key) {
        Object obj = null;
        if (this.map.get(key) instanceof Object[]) {
            Object[] arr = (Object[])((Object[])this.map.get(key));
            obj = Tools.isEmpty(this.request) ? arr : (Tools.isEmpty(this.request.getParameter((String)key)) ? arr : arr[0]);
        } else {
            obj = this.map.get(key);
        }

        return obj;
    }

    public String getString(Object key) {
        return (String)this.get(key);
    }

    public Object put(Object key, Object value) {
        return this.map.put(key, value);
    }

    public Object remove(Object key) {
        return this.map.remove(key);
    }

    public void clear() {
        this.map.clear();
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Set entrySet() {
        return this.map.entrySet();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public Set keySet() {
        return this.map.keySet();
    }

    public void putAll(Map t) {
        this.map.putAll(t);
    }

    public int size() {
        return this.map.size();
    }

    public Collection values() {
        return this.map.values();
    }
}
