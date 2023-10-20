package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.SerializationHelper;

/**
 * Класс отвечающий за команду show
 */
public class Load extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public Load(CollectionHandler collectionHandler) {
        super("load", "");
        this.collectionHandler = collectionHandler;
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {}
        return false;
    }

    @Override
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            SerializationHelper s = new SerializationHelper();
            String csv = s.serializeToCSV(collectionHandler);
            return csv;
        } else {
            return "";
        }
    }

}
