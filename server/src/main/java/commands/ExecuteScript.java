package commands;

import exceptions.ElementAmountException;
import exceptions.RecursionException;
import requests.Request;
import utils.IOHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Command to execute script.
 */
public class ExecuteScript extends AbstractCommand {
    private HashMap<String, Command> map;
    private List<String> prevScripts;
    public ExecuteScript(HashMap<String, Command> map) {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.map = map;
        prevScripts= new ArrayList<String>();
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

    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            try (BufferedReader reader = new BufferedReader(new FileReader(request.getArguments()))) {
                String command;

                while ((command = reader.readLine()) != null) {
                    command += " placeholderArg";
                    String[] tokens = command.split("\\s+");
                    command = tokens[0];
                    String argument = tokens[1];
                    Request buffRequest = new Request(command, argument, null,  request.getUser());
                    if(!command.equals("execute_script")){
                        IOHandler.println("Выполнение команды: " + command);
                        if(map.containsKey(command)){
                            map.get(command).execute(buffRequest);
                        }
                    } else {
                        if(prevScripts.contains(argument)){
                            throw new RecursionException();
                        } else {
                            prevScripts.add(argument);
                            IOHandler.println("Выполнение команды: " + command);
                            if(map.containsKey(command)){
                                map.get(command).execute(buffRequest);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                return "Ошибка при выполнении скрипта: " + request.getArguments();
            } catch (RecursionException re){
                return ("Скрипт или цепочка скриптов не могут выполнять сами себя.");
            }
        }
        prevScripts.clear();
        return "";
    }
}
