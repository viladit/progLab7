package commands;

import exceptions.ElementAmountException;
import utils.IOHandler;

/**
 * Command to print the average of minimal points of all collection elements.
 */
public class AverageOfMinimalPoint extends AbstractCommand {
    public AverageOfMinimalPoint(){
        super("average_of_minimal_point", "вывести среднее значение атрибута minimal point");
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
     * Вывод среднего значения атрибута minimal point всех элементов коллекции.
     * @see app.utils.CollectionHandler
     * @see app.utils.LabWorkCreator
     */
}
