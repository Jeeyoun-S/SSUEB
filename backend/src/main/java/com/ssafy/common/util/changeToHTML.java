package com.ssafy.common.util;

import org.springframework.web.util.HtmlUtils;

public class changeToHTML {
	
	public String changeStringToHTML(String sentence) {
		
		String result = HtmlUtils.htmlEscape(sentence);
		
		return result;
	}
	
	public String changeHTMLToString(String html) {
		
		String result = HtmlUtils.htmlUnescape(html);
		
		return result;
	}
}