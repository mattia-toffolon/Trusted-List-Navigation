import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.Scanner;

public map<String s, Boolean num> findType(){
	
try {
	// try connection
	URL url;
	url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	connection.setRequestMethod("GET");
	
	// creating json object
	JSONArray jsonArr = new JSONArray();
	
	int responseCode = connection.getResponseCode();
	System.out.println("GET Response Code :: " + responseCode);
	
	// if get request status is OK
	if (responseCode == HttpURLConnection.HTTP_OK) {
		// read from response body
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		// parse response body json
		jsonArr = new JSONArray(new JSONTokener(in));
		in.close();
	}
	// iterate over parsed json and print provider names
	Iterator<Object> it = jsonArr.iterator();
	while (it.hasNext()) {
		JSONObject object = (JSONObject) it.next();
						
		//full map types with all the possible service types for the checked countries
		if(nations.get((String)object.get("countryCode")) == true){
			Scanner in =new Scanner((String)object.get("qServiceTypes").toString());
			in.useDelimiter("\\[|\\]|,|\" ");
			String temp;
			while(in.hasNext()) {
				temp = (String)in.next();
				temp = temp.substring(1, temp.length()-1);
				//if the types map do not contains the type, it will be add
				if(!(types.containsKey(temp))) {
					types.put(temp, false);
				}	
			}
		}

	}	

} 
catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}


