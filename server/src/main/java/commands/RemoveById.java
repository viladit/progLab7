package commands;

import Collection.LabWork;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.DatabaseHelper;
import utils.IOHandler;

import java.util.Optional;
/**
 * Класс отвечающий за команду remove_by_id {id}
 */
public class RemoveById extends AbstractCommand{

    private CollectionHandler collectionHandler;
    private DatabaseHelper databaseHelper;

    public RemoveById(CollectionHandler collectionHandler, DatabaseHelper databaseHelper) {
        super("remove_by_id", "удалить элемент коллекции по id");
        this.collectionHandler = collectionHandler;
        this.databaseHelper = databaseHelper;
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
            Optional<LabWork> bufferedLabWork = collectionHandler.getCollection().stream()
                    .filter(person -> person.getId() == Integer.parseInt(request.getArguments()))
                    .findFirst();
            if(bufferedLabWork.get().getCreator().equals(request.getUser().getLogin())){
                bufferedLabWork.ifPresent(collectionHandler::removeLabWork);
                databaseHelper.deleteLabWork((int) bufferedLabWork.get().getId());
            } else {
                return "Нельзя редактировать элементы созданные другими пользователями";
            }
        }
        return "";
    }
}
