package Handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Handler for a connection.
 *
 * It does handle an incoming connection and reads requests and pass each of
 * them to the request handler
 *
 *
 */
public class ConnectionHandler implements Runnable {

    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public ConnectionHandler(Socket s) throws IOException {
        socket = s;
        in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
    }

    /**
     * reads each request and passes it to the request handler. closes a
     * connection if requested
     */
    @SuppressWarnings("deprecation")
    @Override
    public void run() {

        try {

            RequestHandler rhandler = new RequestHandler();
            String line = in.readLine();
            if (line != null) {
            	out.write((rhandler.request(line) + "\n").getBytes("US-ASCII"));
            }

            out.flush();

            socket.close();

        } catch (IOException e) {
            // TODO: write to log
            e.printStackTrace();
        }

    }

}
