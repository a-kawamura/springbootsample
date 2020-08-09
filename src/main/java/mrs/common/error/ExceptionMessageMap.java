package mrs.common.error;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class ExceptionMessageMap {

	@Autowired
	MessageSource messageSource;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final static Map<Class<? extends Exception>, String> messageMappings = Collections
			.unmodifiableMap(new LinkedHashMap(10) {
				private static final long serialVersionUID = 1L;

				{
					put(AlreadyLoginedException.class, "XXX0001");
					put(MethodArgumentNotValidException.class, "XXX0003");
					put(RuntimeException.class, "XXX0002");
					put(Exception.class, "XXX9999");
				}
			});

	public String getMessage(Exception ex) {
		String errorCode = messageMappings.get(ex.getClass());
		if (errorCode == null) {
			errorCode = "XXX9999";
		}
		return messageSource.getMessage(errorCode, null, Locale.JAPAN);
	}

	public String getErrorCode(Exception ex) {
		return Optional.ofNullable(messageMappings.get(ex.getClass()))
				.orElse("XXX9999");
	}

}
