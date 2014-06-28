import java.util.*;

public class InstrumentedAirportScraper implements AirportScraperInterface
{
    AirportScraper airportScraper;
    HashMap<String, Double> searchCache = new HashMap<String,Double>();
    HashMap<String, Integer> searchFrequency = new HashMap<String, Integer>();

    public InstrumentedAirportScraper(AirportScraper airportScraper) {
        this.airportScraper = airportScraper;
    }

    @Override
    public double lookupDistance(String dest) {
        if (searchCache.containsKey(dest)) {
            int i = searchFrequency.get(dest) + 1;
            searchFrequency.put(dest, i);
            return searchCache.get(dest);
        } else {
            double distance = airportScraper.lookupDistance(dest);
            searchCache.put(dest, distance);
            searchFrequency.put(dest, 1);
            return distance;
        }
    }

    // sorted by most commonly called destinations first,
    // ties broken alphabetically. ok to consider case, i.e.,
    // treat AUS and aus differently.  see test suite
    // for exact format
    public List<String> mostCommonDestinations() {
        ValueComparator sorter = new ValueComparator(searchFrequency);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(sorter);
        ArrayList<String> results = new ArrayList<String>();

        //This sorts the HashMap
        sorted_map.putAll(searchFrequency);
        HashSet<String> sorted_keys = (HashSet)sorted_map.keySet();
        Iterator<String> iterator = sorted_keys.iterator();

        while (iterator.hasNext()) {
            results.add(iterator.next());
        }

        return results;
    }

    class ValueComparator implements Comparator<String> {
        Map<String, Integer> base;

        public ValueComparator(Map<String, Integer> base) {
            this.base = base;
        }

        @Override
        public int compare(String a, String b) {
            if (base.get(a) < base.get(b))
                return -1;
            else if (base.get(a) == base.get(b))
                return a.compareTo(b);
            else
                return 1;
        }
    }

}