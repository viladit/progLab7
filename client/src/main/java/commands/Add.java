package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;
/**
 * Command to add a new person to the collection.
 */
public class Add extends AbstractCommand {

    public Add(){
        super("add", "добавить новый элемент в коллекцию");
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
     * Создание объекта класса LabWork и добавление его в коллекцию
     * @see app.utils.CollectionHandler
     * @see app.utils.LabWorkCreator
     */
}
