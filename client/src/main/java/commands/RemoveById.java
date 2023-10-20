package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

import java.util.TreeSet;
/**
 * Command to remove element with id = argument.
 */
public class RemoveById extends AbstractCommand {
    public RemoveById() {
        super("remove_by_id", "удалить элемент коллекции по id");
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
}
