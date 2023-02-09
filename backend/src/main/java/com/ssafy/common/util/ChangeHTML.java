package com.ssafy.common.util;

import org.springframework.web.util.HtmlUtils;

public class ChangeHTML {
	
	public String changeStringToHTML(String sentence) {
		
		// < > & " " ' '
		String result = HtmlUtils.htmlEscape(sentence);
		result = result.replace(";", "&#59;");
		result = result.replace("#", "&#35;");
		result = result.replace("$", "&#36;");
		result = result.replace("(", "&#40;");
		result = result.replace(")", "&#41;");
		result = result.replace("/", "&#47;");
		result = result.replace("?", "&#63;");
		result = result.replace("=", "&#61;");
		result = result.replace(" ", "&nbsp;");
		
		return result;
	}
	
	public String changeHTMLToString(String html) {
		
		String result = html.replace("&nbsp;", " ");
		result = result.replace("&#61;", "=");
		result = result.replace("&#63;", "?");
		result = result.replace("&#47;", "/");
		result = result.replace("&#41;", ")");
		result = result.replace("&#40;", "(");
		result = result.replace("&#36;", "$");
		result = result.replace("&#35;", "#");
		result = result.replace("&#59;", ";");
		result = HtmlUtils.htmlUnescape(result);
		
		return result;
	}
}