package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;

/**
 * Command to clear the collection.
 */
public class Clear extends AbstractCommand{

    private CollectionHandler collectionHandler;

    public Clear(CollectionHandler collectionHandler) {
        super("clear", "очистить коллекцию");
        this.collectionHandler = collectionHandler;
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
            collectionHandler.clear();
        }
        return "";
    }
}
