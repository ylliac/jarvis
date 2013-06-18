package edwin.plugins.freebase.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jayway.jsonpath.JsonPath;

import edwin.app.config.Configuration;
import edwin.core.service.Service;

public class FreebaseService implements Service {

	private static final Logger LOGGER = Logger
			.getLogger(FreebaseService.class);

	//Configuration
	private static final String INI_SECTION = "Freebase";
	private static final String INI_PARAM_KEY = "key";

	public FreebaseService() {
	}

	public void enable() {
	}

	public void disable() {
	}

	public void ready() {
	}

	public String search(String query) {
		String result = "";

		//TODO
//		{
//		  "name": "Jimi Hendrix",
//		  "type": "/common/topic",
//		  "description": null,
//		  "mid": null,
//		  "sort": "description",
//		  "limit": 1
//		}
		
		try {
			String key = Configuration.getInstance().getParam(INI_SECTION, INI_PARAM_KEY);
			
			HttpTransport httpTransport = new NetHttpTransport();
			HttpRequestFactory requestFactory = httpTransport
					.createRequestFactory();
			JSONParser parser = new JSONParser();
			GenericUrl url = new GenericUrl(
					"https://www.googleapis.com/freebase/v1/search");
			url.put("query", query);
			url.put("filter",
					"(any type:/people/person type:/location/citytown)");
			url.put("limit", "10");
			url.put("indent", "true");
			url.put("key", key);
			
			HttpRequest request = requestFactory.buildGetRequest(url);
			HttpResponse httpResponse = request.execute();
			JSONObject response = (JSONObject) parser.parse(httpResponse
					.parseAsString());
			JSONArray queryResults = (JSONArray) response.get("result");
			for (Object queryResult : queryResults) {
				result += JsonPath.read(queryResult, "$.name").toString();
			}
		} catch (IOException e) {
			LOGGER.error(e, e);
		} catch (ParseException e) {
			LOGGER.error(e, e);
		}

		return result;
	}

}
