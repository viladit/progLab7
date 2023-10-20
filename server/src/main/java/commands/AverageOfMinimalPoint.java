package commands;

import Collection.LabWork;
import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Command to print the average of minimal points of all collection elements.
 */
public class AverageOfMinimalPoint extends AbstractCommand{

    private CollectionHandler collectionHandler;
    private TCPServer server;


    public AverageOfMinimalPoint(CollectionHandler collectionHandler, TCPServer server) {
        super("average_of_minimal_point", " вывести среднее значение атрибута minimal point");
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
    /**
     * Очистка коллекции
     * @see utils.CollectionHandler#clear()
     * @see java.util.Collections
     */
    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            long averagePoint = 0;
            for (LabWork labWork : collectionHandler.getCollection()) {
                averagePoint = averagePoint + labWork.getMinimalPoint();
            }
            averagePoint = averagePoint / collectionHandler.getSize();
            try{
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
                output.println("Среднее значение атрибута minimal point: "+averagePoint);
            } catch (IOException ioe) {
                IOHandler.serverMsg(ioe.getMessage());
            }
        }
        return "";

    }
}
