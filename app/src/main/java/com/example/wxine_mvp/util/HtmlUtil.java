package com.example.wxine_mvp.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class HtmlUtil {
	public static void main(String[] args) {
		System.out.println(cleanHtml("<br>fdsafds<div><b>fdsaf</b>"));
	}

	// 保存时最全
	public static String cleanHtmlRelaxed(String htmlStr) {
		try {
			String restr = Jsoup.clean(htmlStr, Whitelist.relaxed().addAttributes("table", "style", "border", "cellspacing", "cellpadding")
					.addAttributes("tr", "style").addAttributes("td", "style").addAttributes("span", "style"));
			return restr;
		} catch (Exception e) {
			return "";
		}
	}

	// 平台取时保留基本
	public static String cleanHtml(String htmlStr) {
		try {
			String restr = Jsoup.clean(htmlStr, Whitelist.basic());
			return restr;
			// CleanerProperties props = new CleanerProperties();
			/*
			 * props.setUseCdataForScriptAndStyle(true);
			 * props.setRecognizeUnicodeChars(true);
			 * props.setUseEmptyElementTags(true);
			 * props.setAdvancedXmlEscape(true);
			 * props.setTranslateSpecialEntities(true);
			 * props.setBooleanAttributeValues("empty");
			 */
			/*
			 * props.setAllowTags("br,b,u,i");
			 * System.out.println("======="+props.getAllowTags()); for
			 * (ITagNodeCondition p : props.getAllowTagSet() ) {
			 * System.out.println(p.toString()); }
			 * 
			 * HtmlCleaner cleaner = new HtmlCleaner(props);
			 * CleanerTransformations transformations = new
			 * CleanerTransformations(); TagTransformation tt = new
			 * TagTransformation("br"); transformations.addTransformation(tt);
			 * tt = new TagTransformation("script");
			 * transformations.addTransformation(tt); //tt = new
			 * TagTransformation("br", "br", true);
			 * //transformations.addTransformation(tt);
			 * cleaner.getProperties().setCleanerTransformations
			 * (transformations);
			 * 
			 * TagNode node = cleaner.clean(htmlStr); TagNode body =
			 * node.findElementByName("body", true); String innerhtml =
			 * cleaner.getInnerHtml(body); return innerhtml;
			 */
		} catch (Exception e) {
			return "";
		}
	}
	
	// 完全清除
	public static String cleanText(String htmlStr) {
		try {
			String restr = Jsoup.clean(htmlStr, Whitelist.none());
			return restr;
		} catch (Exception e) {
			return "";
		}
	}
}
