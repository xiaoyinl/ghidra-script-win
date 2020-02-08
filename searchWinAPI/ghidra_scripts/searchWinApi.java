//Open Windows API documents in docs.microsoft.com 
//@author xiaoyinl
//@category Windows
//@keybinding
//@menupath
//@toolbar searchwinapi.png

import ghidra.app.script.GhidraScript;
import ghidra.app.decompiler.DecompilerLocation;
import java.awt.Desktop;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class searchWinApi extends GhidraScript {

	private static final String apiEndpoint = "https://docs.microsoft.com/api/search?locale=en-us&%24filter=scopes%2Fany%28t%3A+t+eq+%27Desktop%27%29&scoringprofile=search_for_en_us_a_b_test&facet=category&%24skip=0&%24top=5&search=";
 	
	private String getFromURI(String sURL) {
		URL u = null;
		try {
			u = new URL(sURL);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		try (InputStream in = u.openStream()) {
		    return new String(in.readAllBytes(), StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	private String extractFirstUrlFromResponse(String response) {
		try {
			JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
			if (jobj.get("count").getAsInt() == 0) {
				println("The document is not found.");
				return "";
			}
			String url = jobj.get("results").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
			return url;	
		} catch (Exception e) {
			println("Warning: the format of response has changed or the document is not found. The response is: ");
			println(response);
			return "";
		}
	}
	private String getHighlightedStringInDecompiler() {
		if (currentLocation != null && currentLocation instanceof DecompilerLocation) {
			return ((DecompilerLocation)currentLocation).getTokenName();
		}
		return null;
	}
    public void run() throws Exception {
    	String highlightedString = getHighlightedStringInDecompiler();
    	String apiUserInput;
    	if (highlightedString == null || highlightedString.contains("\n")) {
    		apiUserInput = askString("Windows API Lookup", "API: ");
    	} else {
    		apiUserInput = highlightedString;
    	}
    	String apiToFind = URLEncoder.encode(apiUserInput, "UTF-8");
    	String response = getFromURI(apiEndpoint + apiToFind);
    	if (response.length() > 0) {
    		String urlToLaunch = extractFirstUrlFromResponse(response);
        	if (urlToLaunch.length() > 0) {
        		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            		Desktop.getDesktop().browse(new URI(urlToLaunch));
            	}
        	}
    	} else {
    		println("Warning: network error");
    	}
    }
}
