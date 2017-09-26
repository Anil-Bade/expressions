package com.adserving.sitevars;

import java.util.HashMap;
import java.util.Map;

class ExpressionData {
	public Map<String, Object> keys = new HashMap<>();
	public ExpressionData(String expressions) {
		String[] expArr = expressions.split(ExpressionConstants.dataSeparator);
		for (String exp : expArr) {
			String[] entry = exp.split(ExpressionConstants.equal);
			keys.put(entry[ExpressionConstants.key], entry[ExpressionConstants.val]);
		}

	}

}