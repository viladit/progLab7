package commands;

import Collection.LabWork;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;

import java.util.Scanner;
import java.util.TreeSet;
/**
 * Command to remove all elements with min. point lower than argument.
 */
public class RemoveLower extends AbstractCommand{

    private CollectionHandler collectionHandler;

    public RemoveLower(CollectionHandler collectionHandler) {
        super("remove_lower", " удаляет из коллекции все элементы, ниже значения нового элемента");
        this.collectionHandler = collectionHandler;
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            e.printStackTrace();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public String execute(Request request){
        if(!(argCheck(request.getArguments()))){
            TreeSet<LabWork> labWorkCopy = new TreeSet<LabWork>();
            for (LabWork labWork : collectionHandler.getCollection()) {
                labWorkCopy.add(labWork);
            }
            for (LabWork labWork : labWorkCopy) {
                if (labWork.getMinimalPoint() < request.getLabWork().getMinimalPoint()) {
                    collectionHandler.removeLabWork(labWork);
                }
            }
        }
        return "";
    }
}
