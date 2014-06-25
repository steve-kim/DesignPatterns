import java.util.HashMap;
import java.util.List;

public class InstrumentedAirportScraper implements AirportScraperInterface {
    AirportScraper airportScraper;
    HashMap<String, Double> searchCache = new HashMap<String,Double>();
    

    public InstrumentedAirportScraper(AirportScraper airportScraper) {
        this.airportScraper = airportScraper;
    }

    @Override
    public double lookupDistance(String dest) {
        if (searchCache.containsKey(dest)) {
            return searchCache.get(dest);
        } else {
            double distance = airportScraper.lookupDistance(dest);
            searchCache.put(dest, distance);
            return distance;
        }
    }

    // sorted by most commonly called destinations first,
    // ties broken alphabetically. ok to consider case, i.e.,
    // treat AUS and aus differently.  see test suite
    // for exact format
    public List<String> mostCommonDestinations() {
    }

}