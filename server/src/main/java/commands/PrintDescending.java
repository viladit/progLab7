package commands;

import Collection.LabWork;
import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Command to print reverted collection.
 */
public class PrintDescending extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public PrintDescending(CollectionHandler collectionHandler, TCPServer server) {
        super("print_descending", "вывести все элементы коллекции в обратном порядке");
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
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            try{
                String s = "";
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
                String[] arrayForReverse = new String[collectionHandler.getSize()];
                int indexCounter = 0;
                for (LabWork labWork : collectionHandler.getCollection()) {
                    arrayForReverse[indexCounter] = collectionHandler.toString(labWork);
                    indexCounter++;
                }
                for (int i = arrayForReverse.length-1; i >= 0; i--) {
                    s += arrayForReverse[i] + "\n";
                }
                return s;
            } catch (IOException ioe){
                IOHandler.serverMsg(ioe.getMessage());
            }
        }
        return "";
    }
}
