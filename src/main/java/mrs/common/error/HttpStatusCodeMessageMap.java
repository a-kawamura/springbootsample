package mrs.common.error;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class HttpStatusCodeMessageMap {

	@Autowired
	MessageSource messageSource;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final static Map<Integer, String> messageMappings = Collections
			.unmodifiableMap(new LinkedHashMap(10) {
				private static final long serialVersionUID = 1L;

				{
					put(Integer.valueOf(400), "XXX0400");
					put(Integer.valueOf(401), "XXX0401");
					put(Integer.valueOf(403), "XXX0403");
					put(Integer.valueOf(404), "XXX0404");
					put(Integer.valueOf(500), "XXX0500");
				}
			});

	public String getMessage(Integer httpStatusCode) {
		String errorCode = messageMappings.get(httpStatusCode);
		if (errorCode == null) {
			errorCode = "XXX9999";
		}
		return messageSource.getMessage(errorCode, null, Locale.JAPAN);
	}

	public String getErrorCode(Integer httpStatusCode) {
		return Optional.ofNullable(messageMappings.get(httpStatusCode))
				.orElse("XXX9999");
	}

}
