package commands;

import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Command to print collection info.
 */
public class Info extends AbstractCommand{

    private CollectionHandler collectionHandler;
    private TCPServer server;

    public Info(CollectionHandler collectionHandler, TCPServer server) {
        super("info", "вывести информацию о коллекции");
        this.collectionHandler = collectionHandler;
        this.server = server;
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            IOHandler.println("Некорректное кол-во аргументов");
        }
        return false;
    }

    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            try{
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
                output.println("Кол-во элементов в коллекции: "+collectionHandler.getCollection().getClass() + "\n" +
                        "Кол-во элементов в коллекции: "+collectionHandler.getSize() + "\n" +
                        "Дата инициализации коллекции: "+collectionHandler.getInitDate());
            } catch (IOException ioe){
                IOHandler.serverMsg(ioe.getMessage());
            }
        }
        return "";
    }
}
