package commands;

import exceptions.ElementAmountException;
import utils.CommandHelper;
import utils.IOHandler;

/**
 * Command to print the list of command with their description.
 */
public class Help extends AbstractCommand {
    public Help() {
        super("help", "вывести справку о всех доступных командах");
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

    @Override
    public void execute(String arg){
        if(argCheck(arg)){
            IOHandler.println("===========");
            for (String name: CommandHelper.commandList().keySet()) {
                String value = CommandHelper.commandList().get(name);
                IOHandler.println("\u001b[32;1m" + name + "\u001B[0m" + " - " + value + "\n===========");
            }
        }
    }
}
