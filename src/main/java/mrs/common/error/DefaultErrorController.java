package mrs.common.error;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
public class DefaultErrorController implements ErrorController {

	@Autowired
	private ErrorAttributes errorAttributes;

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("error")
	@ResponseBody
	public String handleError(HttpServletRequest request) {
		ServletWebRequest webRequest = new ServletWebRequest(request);
		Map<String, Object> errorAttributes = this.errorAttributes
				.getErrorAttributes(webRequest,
						ErrorAttributeOptions.defaults());
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
}
