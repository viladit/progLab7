package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

/**
 * Command to print reverted collection.
 */
public class PrintDescending extends AbstractCommand {
    public PrintDescending() {
        super("print_descending", " выводит элементы коллекции в обратном порядке");
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
