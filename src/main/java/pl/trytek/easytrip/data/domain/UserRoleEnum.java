package pl.trytek.easytrip.data.domain;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum UserRoleEnum {
    USER("U"),
    MODE("M"),
    ADMIN("A");

    private static final Map<String, UserRoleEnum> MAP;
    private final String code;

    UserRoleEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public String toString() {
        return this.code;
    }

    public static UserRoleEnum parse(String input) {
        return input != null ? (UserRoleEnum)MAP.get(input) : null;
    }

    static {
        Map<String, UserRoleEnum> map = new ConcurrentHashMap();
        UserRoleEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            UserRoleEnum instance = var1[var3];
            map.put(instance.getCode(), instance);
        }

        MAP = Collections.unmodifiableMap(map);
    }
    }
