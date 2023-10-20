package commands;

import Collection.LabWork;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.DatabaseHelper;
import utils.IOHandler;

import java.util.Scanner;
/**
 * Command to add a new labWork to the collection.
 */
public class Add extends AbstractCommand {

    private CollectionHandler collectionHandler;
    private DatabaseHelper databaseHelper;

    public Add(CollectionHandler collectionHandler, DatabaseHelper databaseHelper){
        super("add", "добавить новый элемент в коллекцию");
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
            LabWork labWork = request.getLabWork();
            labWork.setId((long) databaseHelper.saveLabWork(labWork));
            collectionHandler.addLabWork(labWork);
        }
        return "";
    }
}