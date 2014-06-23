import twitter4j.*;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map.Entry;

public class TwitterListener implements StatusListener, Subject {

  private Map<Observer, Set<String>> mapObservers
    = new HashMap<Observer, Set<String>>();

  @Override 
  public void onStatus(Status status) {
    // you need to write some codes here
    System.out.println("OnStatus() called");
    System.out.println(status.getText());
    notifyObservers(status.getText());
  }

  @Override
  public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
    System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
  }

  @Override
  public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
  }

  @Override
  public void onScrubGeo(long userId, long upToStatusId) {
    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
  }

  @Override
  public void onStallWarning(StallWarning warning) {
    System.out.println("Got stall warning:" + warning);
  }

  @Override
  public void onException(Exception ex) {
    ex.printStackTrace();
  }

  @Override // implementing method defined in Subject interface
  public void notifyObservers(String text) {
    // you need to write some codes here
    //Set<String> tweets = new HashSet<String>();
    int matchCounter = 0;
    System.out.println("notifyObservers() called");
    for (Observer user : mapObservers.keySet()) {
        Set<String> tweets = mapObservers.get(user);
        for (String filter : tweets) {
            //Tweet must contain ALL of the terms this user has subscribed to
            if (text.toLowerCase().contains(filter.toLowerCase())) {
                matchCounter++;
                if (matchCounter == tweets.size()) {
                    System.out.println("Calling user update");
                    user.update(text);
                    matchCounter = 0;
                }
//                else
//                    break;
            } else
                continue;
        }
    }
  }

  @Override // implementing method defined in Subject interface
  public boolean removeObserver(Observer observer) {
    boolean result = false;
    // you need to write some codes here
    if (mapObservers.containsKey(observer)) {
        System.out.println("Removing user");
        mapObservers.remove(observer);
        result = true;
    }
    return result;
  }

  @Override  // implementing method defined in Subject interface
  public boolean registerObserver(Observer observer, String track) {
    boolean result = false;
    // you need to write some codes here
    Set<String> subscriptions = new HashSet<String>();
    if (!mapObservers.containsKey(observer)) {
        subscriptions.add(track);
        mapObservers.put(observer, subscriptions);
        result = true;
    } else {
        subscriptions = (HashSet)mapObservers.get(observer);
        if (!subscriptions.contains(track)) {
            subscriptions.add(track);
            mapObservers.put(observer, subscriptions);
            result = true;
        }
    }
    return result;
  }

}
