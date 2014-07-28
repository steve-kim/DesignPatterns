/*
I have modified the smoker classes by making it check only its relevant field first. I.E tobaccoReady only acquire tobacco first.
Upon receiving a lock, it sets its own status to "true". I.E. tobaccoReady set isTobacco = true
I then acquire a mutex that locks out the other consumer threads, and polls to see which other resource as been acquired
The thread is then alerted, and the cigarette is created.
*/
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.List;

public class CSP {
  // agent
  static Semaphore agentSem = new Semaphore(1);
  static Semaphore tobacco = new Semaphore(0);
  static Semaphore paper = new Semaphore(0);
  static Semaphore matches = new Semaphore(0);

  static Semaphore produceReady = new Semaphore(1);

  static boolean isMatch;
  static boolean isTobacco;
  static boolean isPaper;

  static class agentA implements Runnable {
    public void run() {
      while (true) {
        try {
          agentSem.acquire();
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
	System.out.println("agentA about to release tobacco and paper");
        tobacco.release();
        paper.release();  
      }
    }
  }

  static class agentB implements Runnable {
    public void run() {
      while (true) {
        try {
          agentSem.acquire(); 
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
	System.out.println("agentB about to release tobacco and matches");
        tobacco.release();
        matches.release(); 
      }
    }
  }

  static class agentC implements Runnable {
    public void run() {
      while (true) {
        try {
          agentSem.acquire();
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
	System.out.println("agentC about to release paper and matches");
        paper.release();
        matches.release();
      }
    }
  }

  static class tobaccoReady implements Runnable {
    public void run() {
      while (true) {
        try {
          tobacco.acquire();
	  isTobacco = true;
	  produceReady.acquire();
	    if (isPaper) {
		isPaper = false;
		paper.acquire();
	    }	
	    else if (isMatch) {
		isMatch = false;
	    	matches.acquire();
     	    }
	  produceReady.release();
	} catch (Exception e) {
	  e.printStackTrace();
	}
        System.out.println("smoker with matches ready");
        agentSem.release();
      }
    }
  }

  static class matchesReady implements Runnable {
    public void run() {
      while (true) {
        try {
          matches.acquire();
	  isMatch = true;
	  produceReady.acquire();
	    if (isTobacco) {
		isTobacco = false;
		tobacco.acquire();
	    }
	    else if (isPaper) {
		isPaper = false;
	    	paper.acquire();
     	    }
	  produceReady.release();
	} catch (Exception e) {
	  e.printStackTrace();
	}
        System.out.println("smoker with tobacco ready");
        agentSem.release();
      }
    }
  }

  static class paperReady implements Runnable {
    public void run() {
      while (true) {
        try {
          paper.acquire();
	  isPaper = true;
	  produceReady.acquire();
	    if (isTobacco) {
		isTobacco = false;
		tobacco.acquire();
	    }
	    else if (isMatch) {
		isMatch = false;
	    	matches.acquire();
     	    }
	  produceReady.release();
	} catch (Exception e) {
	  e.printStackTrace();
	}
        System.out.println("smoker with paper ready");
        agentSem.release();
      }
    }
  }

  public static void main(String[] args) {
     List<Thread> tList = new ArrayList<Thread>();
     tList.add(new Thread( new CSP.agentA())); 
     tList.add(new Thread( new CSP.agentB())); 
     tList.add(new Thread( new CSP.agentC()));
     tList.add(new Thread( new CSP.tobaccoReady()));
     tList.add(new Thread( new CSP.matchesReady()));
     tList.add(new Thread( new CSP.paperReady()));
     for( Thread t : tList ) {
       t.start();
     }
     try {
       for ( Thread t : tList ) {
         t.join();
       }
     } catch (Exception e) {
       e.printStackTrace();
     }

  }
}
