package pl.trytek.easytrip.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
	VALIDATION_ERR(100),
	RESPONSE_API_ERR(105),
	BAD_REQUEST(400),
	UNPROCESSABLE(422),
	NOT_FOUND_ERR(404);

	private final int code;
	private static final Map<Integer, ExceptionCode> ENUM_MAP;

	static {
		Map<Integer, ExceptionCode> map = new ConcurrentHashMap<>();
		for (ExceptionCode exceptionCode : ExceptionCode.values()) {
			map.put(exceptionCode.getCode(), exceptionCode);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	public static ExceptionCode get(int code) {
		return ENUM_MAP.get(code);
	}
}
