package commands;

import Collection.LabWork;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.DatabaseHelper;
import utils.IOHandler;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * Command to add a new LabWork to the collection if its minimal point is max.
 */
public class AddIfMax extends AbstractCommand {

    private CollectionHandler collectionHandler;
    private DatabaseHelper databaseHelper;

    public AddIfMax(CollectionHandler collectionHandler, DatabaseHelper databaseHelper){
        super("add_if_max", "добавить новый элемент в коллекцию");
        this.collectionHandler = collectionHandler;
        this.databaseHelper = databaseHelper;
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
     * Создание объекта класса Person и добавление его в коллекцию
     * @see utils.CollectionHandler
     * @see utils.LabWorkCreator
     */
    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            Long highestMP = collectionHandler.getCollection().last().getMinimalPoint();
            LabWork labWork = request.getLabWork();
            if (labWork.getMinimalPoint() > highestMP) {
                labWork.setId((long) databaseHelper.saveLabWork(labWork));
                collectionHandler.addLabWork(labWork);
            }
        }
        return "";
    }
}








