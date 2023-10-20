package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

import java.util.Scanner;
import java.util.TreeSet;
/**
 * Command to remove all elements with min. point greater than argument.
 */
public class RemoveGreater extends AbstractCommand {
    public RemoveGreater() {
        super("remove_greater", " удаляет из коллекции все элементы, значение которых выше значения нового элемента");
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
