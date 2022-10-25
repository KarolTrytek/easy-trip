package pl.trytek.easytrip.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

import static java.util.Objects.nonNull;

/**
 * Klasa wyjątków dla aplikacji Easy-Trip (zwracany jako JsonResponse.error)
 */
@Getter
@Setter
public class EasyTripApiException extends RuntimeException {

	Integer errno;

	@Serial
	private static final long serialVersionUID = -4029175130122527234L;



	public EasyTripApiException(){
		super();
	}

	public EasyTripApiException(String message, Throwable throl) {
		super(message, throl);
	}

	public EasyTripApiException(String message){
		super(message);
	}

	public EasyTripApiException(ExceptionCode errno, String message){
		this(errno, message, null);
	}

	public EasyTripApiException(ExceptionCode errno, String message, boolean writableStackTrace){
		this(errno, message, null, writableStackTrace);
	}

	public EasyTripApiException(ExceptionCode errno, String message, Throwable throl){
		super(message, throl);
		if(nonNull(errno))
			this.errno = errno.getCode();
	}

	public EasyTripApiException(ExceptionCode errno, String message, Throwable throl, boolean writableStackTrace){
		super(message, throl, false, writableStackTrace);
		if(nonNull(errno))
			this.errno = errno.getCode();
	}

}
