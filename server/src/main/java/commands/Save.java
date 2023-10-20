package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.FileManager;
import utils.IOHandler;

import java.io.IOException;
/**
 * Command to save collection to file.
 */
public class Save extends AbstractCommand{

    private CollectionHandler collectionHandler;
    FileManager fileManager;
    public Save(CollectionHandler collectionHandler, FileManager fileManager) {
        super("save", "сохраняет коллекцию в файл");
        this.collectionHandler = collectionHandler;
        this.fileManager = fileManager;
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

    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            try{
                fileManager.writeToCSV(collectionHandler);
                IOHandler.println("Коллекция успешно сохранена");
            } catch (IOException e){
                IOHandler.println("Ошибка при записи в файл");
            }
        }
        return "";
    }
}
