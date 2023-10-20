package network;

import commands.*;
import requests.Request;
import Collection.User;
import utils.CommandHelper;
import utils.IOHandler;
import utils.LabWorkCreator;

import java.nio.charset.StandardCharsets;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.io.*;

public class TCPClient {

    private String host = "localhost";
    private int port = 3333;
    private SocketChannel clientSocket;

    public boolean connectToServer() throws IOException {
        try {
            this.clientSocket = SocketChannel.open(new InetSocketAddress(host, port));
            return true;
        } catch (IOException ioe) {
            IOHandler.println("Ошибка при подключении: " + ioe.getMessage());
            return false;
        }
    }

    public void closeConnection() throws IOException {
        this.clientSocket.close();
    }

    public String sendRequest(String input, User user) throws IOException, InterruptedException {
        String[] tokens = input.split("\\s+");
        String command = tokens[0];
        String argument = tokens[1];

        if(!CommandHelper.commandList().containsKey(command)){
            IOHandler.println("Команды " + "\u001B[31m" + command + "\u001B[0m" +" не существует");
            return "";
        }

        if(command.equals("exit")){
            new Exit().execute(argument);
            return "";
        }

        if(command.equals("help")){
            new Help().execute(argument);
            return "";
        }

        if(command.equals("execute_script")){
            new ExecuteScript(CommandHelper.commandList(), this, user).execute(argument);
            return "";
        }

        if(!CommandHelper.argCheckMap().get(command).argCheck(argument)){
            return "";
        }

        if(connectToServer()){
            ObjectOutput objectOutput = new ObjectOutputStream(this.clientSocket.socket().getOutputStream());
            InputStream in = clientSocket.socket().getInputStream();
            if(command.equals("add") || command.equals("update_by_id")|| command.equals("remove_greater")|| command.equals("remove_lower")|| command.equals("add_if_max")){
                objectOutput.writeObject(new Request(command, argument, new LabWorkCreator().labWorkCreate(user), user));
            } else {
                objectOutput.writeObject(new Request(command, argument, null, user));
            }

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String message = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
            closeConnection();
            return(message.trim()+ "\n");

        }
        return "";
    }

    public String sendRequest(Request request) throws IOException, InterruptedException {
        if(connectToServer()){
            ObjectOutput objectOutput = new ObjectOutputStream(this.clientSocket.socket().getOutputStream());
            InputStream in = clientSocket.socket().getInputStream();
            objectOutput.writeObject(request);

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String message = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
            closeConnection();
            return(message.trim()+ "\n");
        }
        return "";
    }

}