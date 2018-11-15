package cz.beny.util.regex;

/**
 * Used to check, if a string matches a URL pattern.
 *
 */
public class URLsDetector {
	private static final String URL_REGEX = "(http://|ftp://|https://|www.)([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";

	/**
	 * Finds URLs in passed string and wraps them in &lta&gt tag
	 * @param str text to check
	 * @return
	 */
	public static String detect(String str) {
		StringBuilder builder = new StringBuilder();

		String[] tokens = str.split(" ");
		for (String token : tokens) {
			if (Regex.matches(token, URL_REGEX)) {
				builder.append("<a href=\"");
				if (token.startsWith("www.")) {
					builder.append("http://");
				}
				builder.append(token).append("\">").append(token)
						.append("</a>");
			} else {
				builder.append(token);
			}
			builder.append(" ");
		}

		return builder.toString();
	}

}
