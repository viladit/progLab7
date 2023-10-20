package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

import java.util.Scanner;
import java.util.TreeSet;
/**
 * Command to update element with id = argument.
 */
public class UpdateById extends AbstractCommand {
    public UpdateById(){
        super("update_by_id", "обновить значение элемента по id");
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
