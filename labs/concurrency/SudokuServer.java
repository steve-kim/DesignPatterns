import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class SudokuServer {
  static int PORT = -1;
  // no matter how many concurrent requests you get,
  // do not have more than three solvers running concurrently
  final static int MAXPARALLELTHREADS = 3;

  // this method exists for testing purposes, since
  // we want to clear out the singleton cache for 
  // subsequent junit tests.
  static void resetcache() {
    //TODOBEGIN(DP)
    //TODOEND(DP)
  }

  public static void start(int portNumber ) throws IOException {
    PORT = portNumber;
    Runnable serverThread = new ThreadedSudokuServer();
    Thread t = new Thread( serverThread );
    t.start();
  }
}

//TODOBEGIN(DP)
class ThreadedSudokuServer implements Runnable {
  public void run() {
      try {
          ServerSocket ss = new ServerSocket(SudokuServer.PORT);
          ExecutorService executorService = Executors.newFixedThreadPool(SudokuServer.MAXPARALLELTHREADS);

          while (true) {
              Socket socket = ss.accept();
              executorService.execute(new SudokuServiceThread(socket));
          }

      } catch (IOException e) {
          e.printStackTrace();
      }

  }
}

class SudokuServiceThread implements Runnable {
    private Socket socket;

    public SudokuServiceThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStreamReader is = new InputStreamReader(socket.getInputStream());
            BufferedReader buf = new BufferedReader(is);
            PrintWriter dos = new PrintWriter(socket.getOutputStream());

            String fromClient = buf.readLine();
            System.out.println("From client: " + fromClient);
            String result = SudokuSolver.solve(fromClient);

            dos.println(result);
            dos.flush();
            dos.close();
            buf.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
//TODOEND(DP)
