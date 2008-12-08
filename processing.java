import processing.serial.*;

Serial port;
Twitter twitter;
Status latestTweet;

long DELTA = 60 * 1000; // poll twitter every minute

long before = 0;

void tweetPrint(Status tweet) {
    println(tweet.getUser().getName() + " @ " + tweet.getCreatedAt() + ":" + tweet.getText());
}

void setup() {
    port = new Serial(this, "COM4", 9600);
  
    try {
        twitter = new Twitter("**USERNAME**", "**PASSWORD**");
    
        latestTweet = twitter.getFriendsTimeline().get(0);
    } catch (TwitterException e) {
        println("Exception:" + e.getMessage());
    }
  
    tweetPrint(latestTweet);
  
    before = millis();
}

void draw() {
    long now = millis();
  
    if (now - before > DELTA) {
        before = now;
    
        java.util.List<Status> tweets = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(latestTweet.getCreatedAt());
            cal.add(Calendar.SECOND, 1);
      
            tweets = twitter.getFriendsTimeline(cal.getTime());
        } catch (TwitterException e) {
            println("Exception:" + e.getMessage());
        }
    
        if (tweets != null) {
            if (tweets.size() > 0) {
                port.write('1'); //LED on!
        
                println("Got " + tweets.size() + " new tweets!");
                latestTweet = tweets.get(0);
                tweetPrint(latestTweet);
            } else {
                println("There are no new tweets!");
            }
        }
    }
}
