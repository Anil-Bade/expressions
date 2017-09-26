package com.adserving.sitevars;

public class ExpressionConstants {
	protected static final int key = 0;
	protected static final int val = 1;
	protected static final String dataSeparator="##"; 
	protected static final String equal="=";
	protected static final String OrExpSplitter=" or ";
	protected static final String AndExpSplitter=" and ";
	protected static final String spaceSplitter=" ";
	protected static final String spaceSplitterRegex=" (?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	 
	protected static final String operator_gt=">";
	protected static final String operator_lt="<";
	protected static final String operator_not_equal="!=";
	protected static final String operator_equal="=";
	protected static final String operator_contains=".contains";
	protected static final String operator_regx="regex";
	protected static final String operator_startsWith="sw";
	protected static final String operator_endsWith="ew";
	
}
 