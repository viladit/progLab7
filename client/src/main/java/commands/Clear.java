package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

/**
 * Command to clear the collection.
 */
public class Clear extends AbstractCommand {
    public Clear() {
        super("clear", "очистить коллекцию");
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
