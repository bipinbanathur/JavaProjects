package com.rtaware.api.test;

import java.util.Map;

import org.json.JSONObject;

import com.rtaware.data.JSONProcessor;

public class JSONTest
{
	public static void testInvalid()
	{
		String[] cases = { "", "  ", "a", "false\"", "trues", "null1", "*", "00", "01", "08", "+0", "+2", "+2.3", "2.3.4", "2e2e2", "2e++0", "2e+-0", "2e--0", "2f", "0x10", "0 0", "0,0", "0 //", "\"", "\"\\", "\"\\\"", "\"\"\"", "\"\\a\"",
				"\"\\x00\"", "\"\\u+000\"", "\"\\u-000\"", "\"\u0000\"", "\"\n\"", "\"a\tb\"", "\"ab\rcd\"", "[", "]", "{", "}", "[}", "[,]", "[0,]", "[5 4]", "[5, 4,]", "[\"a\" \"b\"]", "[\"cc\" 3]", "[[]", "][", "[\"]", "{abc:0}", "{null:true}",
				"{\"a\":1,}", "{\"a\":1 \"b\":2}", "{}{}", "{{}}", };
		String status = "";
		String emsg = "";
		System.out.println("------------------------------------------------------------------------");
		System.out.format("%-30s%-30s%-60s", "INVALID CASE", "IS PROCESSED", "REASON");
		System.out.println("");
		System.out.println("------------------------------------------------------------------------");
		for (String cs : cases)
		{
			/*
			 * try { JSONProcessor jp = new JSONProcessor(cs); status = "FAIL";
			 * emsg = ""; } catch (Exception e) { // TODO: handle exception
			 * status = "PASS"; emsg = e.getMessage(); }
			 */

			if (JSONProcessor.isJSONValid(cs))
			{
				status = "YES";
			}
			else
			{
				status = "NO";
				emsg = "Malformed JSON is not processed";
			}
			System.out.format("%-30s%-30s%-60s", cs, status, emsg);
			System.out.println("");

		}
	}

	public static void testWhitespaceEquivalence()
	{
		String[][] cases = { { "-0", " -0" }, { "-1", "-1\t" }, { "2", "2\n" }, { "3", "\t 3\r\r" }, { "4.555", "  4.555 " }, { "6.7e-76", "\n6.7e-76\t" }, { "null", "  null \n" }, { "false", "false  " }, { "true", "  true" },
				{ "6.7e-76", "\n6.7e-76\t" }, { "[]", "[  ]" }, { "[null]", "[  null ] " }, { "[8,9]", "[  8,  9 ]" }, { "{}", "\t{\n\r}\t\t" }, { "[{}]", " [  {  }] " }, { "[[[]],[],[],{}]", "[[[  ]],  []  ,[],{} ]" },
				{ "{\"a\":{}}", "{  \"a\" : {}}" }, { "{\"bb\":[],\"c\":0}", "{  \"bb\" : [ ] , \"c\"  :0 }" }, { "{\"d\":false,\"\\b\":[true]}", "  {\"d\"  :false ,\"\\b\": [ true]  } " }, };
		String status = "";
		String emsg = "";
		System.out.println("------------------------------------------------------------------------");
		System.out.format("%-30s%-30s%-60s", "INVALID CASE", "IS PROCESSED", "REASON");
		System.out.println("");
		System.out.println("------------------------------------------------------------------------");

		for (String[] cs : cases)
		{
			if (JSONProcessor.isJSONValid(cs[0]))
			{
				status = "YES";
			}
			else
			{
				status = "NO";
				emsg = "Malformed JSON is not processed";
			}
			System.out.format("%-30s%-30s%-60s", cs[0], status, emsg);
			System.out.println("");
			if (JSONProcessor.isJSONValid(cs[1]))
			{
				status = "YES";
			}
			else
			{
				status = "NO";
				emsg = "Malformed JSON is not processed";
			}
			System.out.format("%-30s%-30s%-60s", cs[1], status, emsg);
			System.out.println("");

		}
	}

	public static void printInfo(String data, String key)
	{
		System.out.println("Value of " + key + " is " + JSONProcessor.getValue(data, key));
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		
		String data = "{ \"alpha\": null, \"beta\" : [9, [1,2,3], 777], \"gamma\": [\"x\", 3.21,{\"y\": 5,\"z\": 6} ]	} ";

		String data2 = "{ \"alpha\": null, \"beta\" : [9, [1,2,3], 777], \"gamma\": [\"x\", 3.21,{\"y\": 5,\"z\": 6} ],\"delta\":{\"a1\":2,\"a2\":3,\"a3\":true}	} ";

		try
		{
			System.out.println("------------------------------------------------------------------------");
			// Converting JSON string to Map
			JSONObject jo = new JSONObject(data2);
			Map<String, Object> map = JSONProcessor.jsonToMap(jo);
			JSONProcessor.displayJSONMAP(map);
			System.out.println("------------------------------------------------------------------------");
			// Get value of key from the JSON String
			System.out.println("Value of beta[1][0] is " + JSONProcessor.getValue(data2, "beta[1][0]"));
			System.out.println("Value of delta.a3 is " + JSONProcessor.getValue(data2, "delta.a3"));
			if (JSONProcessor.getBoolean(data2, "delta.a3"))
			{
				System.out.println("This is True");
			}
			System.out.println("------------------------------------------------------------------------");
			// Test for invalid inputs
			testInvalid();
			System.out.println("------------------------------------------------------------------------");
			// Test for Whitespace inputs
			testWhitespaceEquivalence();

			System.out.println("------------------------------------------------------------------------");
			String testdata1 = "{" + "\"name\":\"OTSi vCenter\"," + "\"description\":\"a vCenter server in the OTSi Lab\"," + "\"enabled\":true," + "\"icon\":\"/csa/api/blobstore/vmware_vcenter.png?tag=categories&tag=provider_type\"," + "\"type\":{"
					+ "\"@self\":\"/csa/api/resource/provider/type/90d96588360da0c701360da0ee93000f\"," + "\"ext\": {" + "\"csa_name_key\": \"VMWARE_VCENTER\"," + "\"csa_critical_system_object\": \"true\"" + "}" + "}," + "\"access_point\":{"
					+ "\"username\":\"api_user\"," + "\"password\":\"api_pwd\"," + "\"uri\":\"https://access.foo.bar:443\"" + "}" + "}";

			JSONProcessor jp = new JSONProcessor(testdata1);
			JSONTest.printInfo(testdata1, "description");
			JSONTest.printInfo(testdata1, "type.ext.csa_critical_system_object");
			System.out.println("------------------------------------------------------------------------");

			String testdata2 = "{" + "\"problems\": [{" + "\"Diabetes\": [{" + "\"medications\": [{" + "\"medicationsClasses\": [{" + "\"className\": [{" + "\"associatedDrug\": [{" + "\"name\": \"asprin\"," + "\"dose\": \"2\","
					+ "\"strength\": \"500 mg\"" + "}]," + "\"associatedDrug#2\": [{" + "\"name\": \"somethingElse\"," + "\"dose\": \"a\"," + "\"strength\": \"500 mg\"" + "}]" + "}]" + "}]" +

					"}]" + "}]" +

					"}]" + "}";

			JSONProcessor jp1 = new JSONProcessor(testdata2);

			JSONTest.printInfo(testdata2, "problems[0].Diabetes[0].medications[0].medicationsClasses[0].className[0].associatedDrug[0].name");
			System.out.println("------------------------------------------------------------------------");

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
