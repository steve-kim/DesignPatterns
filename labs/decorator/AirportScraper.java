import java.net.*;
import java.io.*;
import java.util.*;

class AirportScraper implements AirportScraperInterface {

    String origin;

    public AirportScraper() {
	origin = null;
    }

    public AirportScraper(String airportCode) {
	origin = airportCode;
    }

    public String toString() {
	return "Originating Airport:" + origin;
    }

    // utility method, pulls distance from html
    private static double extractDistance(String rawText) {
	// URL open returns html with distance in this format:
	// ...  kilometres (1527 miles)</p> ...
	String anchor1 = " kilometres (";
	String anchor2 = " miles)</p>";
	int offset = rawText.indexOf( anchor1 ) + anchor1.length();
	int end = rawText.indexOf( anchor2 );
	String distanceString = rawText.substring( offset, end );
	double result = new Double( distanceString );
	return result;
    }

    public double lookupDistance(String dest) {
	try {
	    String airportUrl = "http://www.world-airport-codes.com/dist/?";
      String completeUrl =
          airportUrl + "a1=" + origin + "&" + "a2=" + dest;
      //Adding code to make HTTP request go through
      System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
      URL kUrl = new URL( completeUrl );
      StringBuilder sb = new StringBuilder();
      String str = null;
      BufferedReader in =
          new BufferedReader(new InputStreamReader(kUrl.openStream()));
      while ((str = in.readLine()) != null) {
	  sb.append( str );
      }
      in.close();
      return extractDistance( sb.toString() );
	} catch (Exception e) {
	    e.printStackTrace();
	    return -1.0;
	}
    }
}
