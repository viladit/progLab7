package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;
/**
 * Command to add a new person to the collection if its minimal point is max.
 */
public class AddIfMax extends AbstractCommand {
    public AddIfMax(){
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение максимальное среди всех элементов");
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
     * Создание объекта класса LabWork и добавление его в коллекцию, если его значение максимальное среди всех элементов
     * @see app.utils.CollectionHandler
     * @see app.utils.LabWorkCreator
     */
}
