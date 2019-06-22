package mfllc.silktest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String makeClassName(String name) {
		int index = name.indexOf(".");
		if (index != -1) name = name.substring(0, index);
		return makeIdentifier(name, true);
	}
	
	public static String makeIdentifier(String name, boolean capitalizeFirst) {
		Matcher matcher = Pattern.compile("(\\w+)\\W*").matcher(name);
		StringBuilder result = new StringBuilder();
		while (matcher.find()) {
			String subname = matcher.group(1);
			if (subname.isEmpty()) continue;
			if (result.length() == 0 && !capitalizeFirst)
				result.append(subname);
			else
				result.append(subname.substring(0, 1).toUpperCase()).append(subname.substring(1));
		}
		return result.toString();
	}
}
