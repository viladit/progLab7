package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

/**
 * Command to print collection info.
 */
public class Info extends AbstractCommand {
    public Info() {
        super("info", "вывести информацию о коллекции");
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
