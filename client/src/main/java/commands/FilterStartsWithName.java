package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

import java.util.TreeSet;
/**
 * Command to print all elements starts with argument.
 */
public class FilterStartsWithName extends AbstractCommand {
    public FilterStartsWithName() {
        super("filter_contains_name", "выводит элементы, значение поля name которых начинается с заданной подстроки");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            IOHandler.println("Некорректное кол-во аргументов");
        }
        return false;
    }
}
