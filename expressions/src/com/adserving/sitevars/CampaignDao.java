package com.adserving.sitevars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.CommunicationException;

public class CampaignDao {
	private String expression;
	private int campaignId;

	public CampaignDao(String targeting, int campaignId) {
		this.expression = targeting;
		this.campaignId = campaignId;

	}

	public boolean checkAvailability(ExpressionData req_data) throws InvalidExpressionException {
		Map<Integer, List<Boolean>> sectionResults = new HashMap<>();
		String str[] = expression.split(ExpressionConstants.OrExpSplitter);
		outerloop: for (int section = 0; section < str.length; section++) {
			String[] arr = str[section].split(ExpressionConstants.AndExpSplitter);
			for (String a : arr) {
				String keyVal[] = a.split(ExpressionConstants.spaceSplitterRegex);
				if (keyVal.length != 3) {
					throw new InvalidExpressionException("Expression is not valid near " + a);
				}
				boolean result = checkExpression(req_data.keys.get(keyVal[0]), keyVal[1], keyVal[2].replaceAll("\"",""));
				List<Boolean> sectionList=sectionResults.get(section);
				if(sectionList==null)
					sectionList=new ArrayList<>();
				sectionList.add(result);
				sectionResults.put(section,sectionList);
			}
			if(sectionResults.get(section).contains(false))
				continue outerloop;
			else
				return true;

		}
		return false;
	}

	private boolean checkExpression(Object targetingVal, String opr, String qryVal) {
		if(targetingVal==null)
		{
			return false;
		}
		Double dval = null;
		try {
			dval = Double.parseDouble(targetingVal.toString());
		} catch (Exception e) {
			e.getMessage();
		}
		try{
		if (opr.equals(ExpressionConstants.operator_gt)) {
			
			Double val=convertValue(qryVal,opr);
			if(val==null){
				return false;
			}
			if (dval != null && dval > val)
				return true;
			else
				return false;
		} else if (opr.equals(ExpressionConstants.operator_lt)) {
			Double val=convertValue(qryVal,opr);
			if(val==null){
				return false;
			}
			if (dval < val)
				return true;
			else
				return false;
		} else if (opr.equals(ExpressionConstants.operator_equal)) {
			if (targetingVal.toString().equalsIgnoreCase(qryVal))
				return true;
			else
				return false;
		} else if (opr.equals(ExpressionConstants.operator_not_equal)) {
			if (!targetingVal.toString().equalsIgnoreCase(qryVal))
				return true;
			else
				return false;
		} else if (opr.equals(ExpressionConstants.operator_contains)) {
			if (targetingVal.toString().toUpperCase().contains(qryVal.toUpperCase()))
				return true;
			else
				return false;

		}else if(opr.equals(ExpressionConstants.operator_regx)){
			Pattern r = Pattern.compile(qryVal);
			 Matcher m = r.matcher(targetingVal.toString());
			 if(m.find())
				 return true;
			 else
				 return false;
			
		}else if(opr.equals(ExpressionConstants.operator_startsWith)){
			if (targetingVal.toString().startsWith(qryVal))
				 return true;
			 else
				 return false;
			
		}
		else if(opr.equals(ExpressionConstants.operator_endsWith)){
			if (targetingVal.toString().endsWith(qryVal))
				 return true;
			 else
				 return false;
			
		}
		else{
			throw new WARNING_InvalidExpressionOperator("Operator " + opr + " not found ! \n this expression will be considered as false !");
		}
		
		}catch (WARNING_InvalidExpressionOperator e) {
			e.printStackTrace();
		}
		return false;

	}
	private Double convertValue(String qryVal, String operator)
	{
		
		try{
			return Double.parseDouble(qryVal);
		 	
		}catch(NumberFormatException e){
			System.out.println("Value "+qryVal +" is not valid for "+operator+" operator !" );
		return null;	
		}
	}

}
