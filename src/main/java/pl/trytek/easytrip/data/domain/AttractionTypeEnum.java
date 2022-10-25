package pl.trytek.easytrip.data.domain;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum AttractionTypeEnum {

    MUSEUM("M"),
    PARK("P"),
    BUILDING("B"),
    WATER("W"),
    RELIGIOUS("R");

    private static final Map<String, AttractionTypeEnum> MAP;

    private final String code;

    AttractionTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static AttractionTypeEnum parse(String input) {
        return (input != null ? MAP.get(input) : null);
    }

    static {
        Map<String, AttractionTypeEnum> map = new ConcurrentHashMap<>();
        for (AttractionTypeEnum instance : AttractionTypeEnum.values()) {
            map.put(instance.getCode(),instance);
        }
        MAP = Collections.unmodifiableMap(map);
    }
}
