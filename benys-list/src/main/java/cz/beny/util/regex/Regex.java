package cz.beny.util.regex;

import java.util.regex.Pattern;

public class Regex {

	/**
	 * Returns true, if the passed string matches the passed pattern.
	 * @param str string to check
	 * @param pattern
	 */
  public static boolean matches(String str, String pattern) {
    return Pattern.compile(pattern).matcher(str).matches();
  }

}