package commands;

import Collection.LabWork;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;

import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Command to remove all elements with min. point greater than argument.
 */
public class RemoveGreater extends AbstractCommand{

    private CollectionHandler collectionHandler;

    public RemoveGreater(CollectionHandler collectionHandler) {
        super("remove_greater", " удаляет из коллекции все элементы, превышающие заданный");
        this.collectionHandler = collectionHandler;
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(!(arg.equals("placeholderArg"))) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            IOHandler.println("Некорректное кол-во аргументов");
        }
        return false;
    }

    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            int argument = 999999;
            TreeSet<LabWork> labWorkCopy = new TreeSet<LabWork>();
            for (LabWork labWork : collectionHandler.getCollection()) {
                labWorkCopy.add(labWork);
            }
            for (LabWork labWork : labWorkCopy) {
                if (labWork.getMinimalPoint() > request.getLabWork().getMinimalPoint()) {
                    collectionHandler.getCollection().remove(labWork);
                }
            }
        }
        return "";
    }
}
