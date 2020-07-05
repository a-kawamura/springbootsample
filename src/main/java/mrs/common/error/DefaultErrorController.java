package mrs.common.error;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultErrorController extends AbstractErrorController {

	public DefaultErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping(path = "error", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String handleError(HttpServletRequest request, Model model) {
		Map<String, Object> errorAttributes = getErrorAttributes(request);
		StringBuilder errorDetails = new StringBuilder();
		errorAttributes.forEach((attribute, value) -> {
			errorDetails.append("<tr><td>").append(attribute)
					.append("</td><td><pre>").append(value)
					.append("</pre></td></tr>");
		});
		return String.format(
				"<html><head><style>td{vertical-align:top;border:solid 1px #666;}</style>"
						+ "</head><body><h2>Error Page</h2><table>%s</table></body></html>",
				errorDetails.toString());
	}

	@RequestMapping(path = "error")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleErrorForRest(
			HttpServletRequest request, Model model) {
		Map<String, Object> body = getErrorAttributes(request);
		return new ResponseEntity<>(body, getStatus(request));
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
		Map<String, Object> errorAttributes = getErrorAttributes(request,
				ErrorAttributeOptions.defaults());
		Exception e = (Exception) request.getAttribute("exception");
		if (e != null) {
			errorAttributes.put("exception", e.getClass().getCanonicalName());
			errorAttributes.put("message", e.getMessage());
		}
		return errorAttributes;
	}
}
