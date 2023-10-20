package commands;

import Collection.LabWork;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.IOHandler;
import utils.LabWorkCreator;

import java.util.Scanner;
import java.util.TreeSet;
/**
 * Command to update element with id = argument.
 */
public class UpdateById extends AbstractCommand {

    private CollectionHandler collectionHandler;

    public UpdateById(LabWorkCreator labWorkCreator, CollectionHandler collectionHandler){
        super("update_by_id", "обновить значение элемента по id");
        this.collectionHandler = collectionHandler;
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            Integer.parseInt(arg);

            return true;
        } catch (ElementAmountException e) {
            IOHandler.println("Некорректное кол-во аргументов");
        } catch (NumberFormatException ex) {
            IOHandler.println("Введите число");
        }
        return false;
    }

    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            for(LabWork labWork :  collectionHandler.getCollection()){
                if(labWork.getId()==(Integer.parseInt(request.getArguments()))){
                    collectionHandler.removeLabWork(labWork);
                    LabWork nLabWork = request.getLabWork();
                    nLabWork.setId(Long.parseLong(request.getArguments()));
                    collectionHandler.addLabWork(nLabWork);
                }
            }
        }
        return "";
    }
}
