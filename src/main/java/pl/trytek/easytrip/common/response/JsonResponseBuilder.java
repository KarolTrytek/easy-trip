package pl.trytek.easytrip.common.response;

import org.springframework.http.HttpStatus;
import pl.trytek.easytrip.common.exception.*;

public class JsonResponseBuilder {
	
	private JsonResponseBuilder() {}
	
	public static <T> JsonResponse<T> ok() {
        return ok("OK");
    }

    public static <T> JsonResponse<T> ok(T payload) {
        return ok(payload, "OK");
    }
    
    public static <T> JsonResponse<T> ok(String msg) {
        return ok(null, msg);
    }
    
    public static <T> JsonResponse<T> ok(T payload, String msg) {
        return new JsonResponse<>(HttpStatus.OK.value(), msg, payload);
    }

    public static <T> JsonResponse<T> error(HttpStatus httpStatus) {
        return error(httpStatus, null, "");
    }
    
    public static <T> JsonResponse<T> error(HttpStatus httpStatus, T payload) {
        return error(httpStatus, payload, "");
    }

    public static <T> JsonResponse<T> error(HttpStatus httpStatus, T payload, String msg) {
        return new JsonResponse<>(httpStatus.value(), msg, payload);
    }

    public static <T> JsonResponse<T> error(int status) {
        return error(status, null, "");
    }
    
    public static <T> JsonResponse<T> error(int status, T payload) {
        return error(status, payload, "");
    }

    public static <T> JsonResponse<T> error(int errorStatus, T payload, String msg) {
        return new JsonResponse<>(errorStatus, msg, payload);
    }
    public static <T> JsonResponse<T> error(HttpStatus httpStatus, String msg) {
        return new JsonResponse<>(httpStatus.value(), msg, null);
    }
    public static <T> JsonResponse<T> error(ExceptionCode status, String msg){
	    return new JsonResponse<>(status.getCode(), msg, null);
    }
}
