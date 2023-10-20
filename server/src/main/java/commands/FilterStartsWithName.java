package commands;

import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Command to print all elements starts with argument.
 */
public class FilterStartsWithName extends AbstractCommand{

    private CollectionHandler collectionHandler;
    private TCPServer server;

    public FilterStartsWithName(CollectionHandler collectionHandler, TCPServer server) {
        super("filter_starts_with_name", "выводит элементы, значение поля name которых содержит заданную подстроку");
        this.collectionHandler = collectionHandler;
        this.server = server;
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
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
                collectionHandler.getCollection().stream()
                        .filter(labWork -> labWork.getName().contains(request.getArguments()))
                        .forEach(labWork -> collectionHandler.printLabWork(labWork, output));
            } catch (IOException ioe){
                IOHandler.println(ioe.getMessage());
            }
        }
        return "";
    }
}
