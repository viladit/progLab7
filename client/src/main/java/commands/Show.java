package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

/**
 * Command to print collection elements.
 */
public class Show extends AbstractCommand {
    public Show() {
        super("show", "вывести все элементы коллекции");
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
