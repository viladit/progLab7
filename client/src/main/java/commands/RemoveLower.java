package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

/**
 * Command to remove all elements with min. point lower than argument.
 */
public class RemoveLower extends AbstractCommand {
    public RemoveLower() {
        super("remove_lower", " удаляет из коллекции все элементы, значение которых ниже значения нового элемента");
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
}
