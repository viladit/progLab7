package commands;

import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Command to print collection elements.
 */
public class Show extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public Show(CollectionHandler collectionHandler, TCPServer server) {
        super("show", "вывести все элементы коллекции");
        this.collectionHandler = collectionHandler;
        this.server = server;
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            try{
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
                output.println("Некорректное кол-во аргументов");
            } catch (IOException ioe){
                IOHandler.println(ioe);
            }
        }
        return false;
    }

    @Override
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            StringWriter sw = new StringWriter();
            PrintWriter output = new PrintWriter(sw, true);

            collectionHandler.printLabWorkList(output);

            return sw.toString();
        } else {
            return "";
        }
    }
}