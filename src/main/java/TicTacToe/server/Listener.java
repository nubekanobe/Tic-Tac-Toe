package TicTacToe.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import TicTacToe.Game.Message;
import TicTacToe.gameScreen;
import TicTacToe.Game.Game;

public class Listener implements Runnable
{
    Socket socket;
    gameScreen UI;
    boolean shutdown = false;

    public Listener(Socket socket, gameScreen UI) // gameOver
    {
        this.socket = socket;
        this.UI = UI;
    }
    @Override
    public void run() {
        try {
            //System.out.println("listener now running");
            InputStream input = socket.getInputStream();
            //System.out.println("Input stream working");
            //System.out.println("socket is connected -- " + socket.isConnected());
            ObjectInputStream objectInput = new ObjectInputStream(input);
            //System.out.println("Object stream working");
            //System.out.println("initialize inputs");
            Message message = null;
            Object object = new Object();

            while (!shutdown)
            {
                //System.out.println("start while loop");
                try {
                    object = objectInput.readObject();
                    //System.out.println("read object");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (object instanceof Game)
                {
                    //System.out.println("game instance");
                    Game game = ((Game)object);
                    //System.out.println("buttons -- " + game.buttons());
                    //System.out.println(game.toString());
                    UI.update(((Game)object));
                }
                else if (object instanceof Message)
                {
                    //System.out.println("message instance");
                    message = (Message)object;
                    if (message == null)
                    {
                        continue;
                    }
                    else if (message.message.contains("/shutdown"))
                    {
                        //System.out.println("shutdown received");
                        UI.UIToggleOff();
                        shutdown = true;
                        UI.setErrorLabel("The other player has quit, taking you back to the main menu....");

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        UI.handleOptionsQuit();
                    }
                    else if (message.message.contains("/serverShutdown"))
                    {
                        try {
                        new Thread(new Notifier(socket, "/serverShutdown", UI)).start();

                        //System.out.println("ServerShutdown received");
                        UI.UIToggleOff();
                        shutdown = true;
                        UI.setErrorLabel("We apologize, but the server had shutdown, taking you back to the main menu....");

                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        UI.handleOptionsQuit();
                    }
                    else if (message.message.contains("/yourTurn"))
                    {
                        //System.out.println("Your turn");
                        UI.UIToggleOn();
                    }
                    else if (message.message.contains("/notYourTurn"))
                    {
                        //System.out.println("Not your turn");
                        UI.UIToggleOff();
                    }
                    else if (message.message.contains("/invalid"))
                    {
                        //System.out.println("Invalid input");
                        UI.setErrorLabel("A issue has occurred, your board has been updated. Please make a new move.");
                    }
                    else if (message.message.contains("/valid"))
                    {
                        //System.out.println("Valid input");
                        UI.setErrorLabel("");

                    }
                    else if (message.message.equalsIgnoreCase("/1") || message.message.equalsIgnoreCase("/2"))
                    {
                        //System.out.println("new game message");
                        UI.setPlayerLabel(String.valueOf(message.message.charAt(1)));
                        //System.out.println("player label set " + message.message.charAt(1));
                        //System.out.println("new game message end " + message.message);

                    }
                    else if (message.message.contains("/gameOver"))
                    {
                        UI.gameOver();
                    }
                    else if (message.message.contains("/message"))
                    {
                        UI.newMessage(message.chatMessage);
                        //System.out.println("message received");
                    }
                    else
                    {
                        System.out.println("invalid MESSAGE received");
                    }
                }
                else
                {
                    System.out.println("invalid object received");
                }
                object = null;
            }

        }
        catch (IOException e) {
            if (e.getMessage() != null)
            {
                if (e.getMessage().equals("Socket closed")) {
                    System.out.println("Socket is closed. -- Listener");
                } else {
                    throw new RuntimeException(e);
                }
                return;
            }
            throw new RuntimeException(e);

        }
    }

}
