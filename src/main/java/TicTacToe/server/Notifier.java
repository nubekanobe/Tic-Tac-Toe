package TicTacToe.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import TicTacToe.gameScreen;

public class Notifier implements Runnable {
    Socket socket;
    String message;
    gameScreen UI;

    public Notifier(Socket socket, String message, gameScreen UI)
    {
        this.socket = socket;
        this.message = message;
        this.UI = UI;
    }
    @Override
    public void run() {
        if (message == null)
        {
            return;
        }
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //sending right message in correlation to message information
            if (message.contains("/move"))
            {
//                System.out.println(message.charAt(message.length()-1) + " -- move");
                out.println(message);
            }
            else if (message.contains("/mode"))
            {
//                System.out.println(message.charAt(message.length()-1));

                out.println(message.charAt(message.length()-1) + " -- mode");
            }
            else if (message.contains("/quit"))
            {
                out.println("/quit");
                UI.closeSocket();
            }
            else if (message.contains("/serverShutdown"))
            {
//                System.out.println(" server shutting down signal");
                out.println("/serverShutdown");
            }
            else if (message.contains("/difficulty"))
            {
//                System.out.println(message.charAt(message.length()-1) + " -- difficulty");
                out.println(message);
            }
            else if (message.contains("/clearBoard"))
            {
//                System.out.println(" -- clear board");
                out.println(message);
            }
            else if (message.contains("/switchSides"))
            {
//                System.out.println(" -- switch sides");
                out.println(message);
            }
            else if (message.contains("/message"))
            {
//                System.out.println(message + " -- message");
                out.println(message);
            }
            else
            {
                System.out.println("invalid message -- " + message);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
