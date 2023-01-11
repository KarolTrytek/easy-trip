package pl.trytek.easytrip.common.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.trytek.easytrip.common.exception.EasyTripApiException;
import pl.trytek.easytrip.common.exception.ExceptionCode;
import pl.trytek.easytrip.common.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * ControllerAdvice obsługi błędów kontrolerów REST.
 */
@ControllerAdvice
@Slf4j
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Obsługuje wszystkie błędy.
     *
     * @param ex     wyjątek
     * @param request request
     * @return opakowana w JsonResponse odpowiedź
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public JsonResponse<List<StackTraceElement>> handleAll(final Exception ex , final WebRequest request) {
        logger.error("Exception" , ex);
        return JsonResponseBuilder.error(HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getLocalizedMessage());
    }

    /**
     * Obsługuje błąd IllegalArgumentException.
     *
     * @param ex     wyjątek
     * @param request request
     * @return opakowana w JsonResponse odpowiedź
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public JsonResponse<Object> handleIllegalArgumentException(final Exception ex , final WebRequest request) {
        logger.error("IllegalArgumentException" , ex);
        List<StackTraceElement> stackTrace = Arrays.asList(ex.getStackTrace());
        if(!stackTrace.isEmpty() && stackTrace.stream().anyMatch(st ->  st.getFileName().equals("Assert.java")))
            return JsonResponseBuilder.error(ExceptionCode.VALIDATION_ERR.getCode(), null, ex.getLocalizedMessage());
        else
            return JsonResponseBuilder.error(HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getLocalizedMessage());
    }

    /**
     * Obsługuje błąd ResponseStatusException.
     *
     * @param ex     wyjątek
     * @param request request
     * @return obiekt opakowany w ResponseEntity
     */
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(final ResponseStatusException ex, final WebRequest request) {
        logger.error("ResponseStatusException", ex);
        ex.setStackTrace(new StackTraceElement[0]);
        return new ResponseEntity<>(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Obsługuje błąd EOpiekaApiException.
     *
     * @param ex     wyjątek
     * @param request request
     * @return opakowana w JsonResponse odpowiedź
     */
    @ExceptionHandler({EasyTripApiException.class})
    @ResponseBody
    public JsonResponse<Object> handleEOpiekaApiException(final EasyTripApiException ex, final WebRequest request) {
        logger.error("EOpiekaApiException", ex);
        if (nonNull(ex.getErrno()))
            return JsonResponseBuilder.error(ex.getErrno(), null, ex.getMessage());
        else
            return JsonResponseBuilder.error(HttpStatus.UNPROCESSABLE_ENTITY, null, ex.getMessage());
    }

//    /**
//     * Obsługuje błąd SystemUserAuthorizationException.
//     *
//     * @param ex     wyjątek
//     * @param request request
//     * @return opakowana w JsonResponse odpowiedź
//     */
//    @ExceptionHandler({SystemUserAuthorizationException.class})
//    @ResponseBody
//    public JsonResponse<Object> handleSystemUserAuthorizationException(final SystemUserAuthorizationException ex, final WebRequest request) {
//        logger.error("SystemUserAuthorizationException", ex);
//        return JsonResponseBuilder.error(HttpStatus.UNAUTHORIZED, null, ex.getMessage());
//    }

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("MethodArgumentNotValidException", ex);
        ex.setStackTrace(new StackTraceElement[0]);
        return new ResponseEntity<>(JsonResponseBuilder.error(ExceptionCode.VALIDATION_ERR.getCode(), null,
                ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining(StringUtils.COMMA))),
                HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        return handleExceptionInternal(ex, ex.getMessage(), headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Obsługuje błąd AccessDeniedException.
     *
     * @param ex     wyjątek
     * @param request request
     * @return obiekt opakowany w ResponseEntity
     */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final Exception ex , final WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}
