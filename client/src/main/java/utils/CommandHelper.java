package utils;

import java.io.Serializable;
import java.util.HashMap;
import commands.*;

/**
 * Вспомогательный класс с парами (название команды, описание команды)
 */
public class CommandHelper implements Serializable {
    public static HashMap<String, String> commandList(){

        HashMap<String, String> commands = new HashMap<String, String>();
        commands.put("help", "вывести справку о всех доступных командах");
        commands.put("info", "вывести информацию о коллекции");
        commands.put("show", "вывести все элементы коллекции");
        commands.put("add", "добавить новый элемент в коллекцию");
        commands.put("update_by_id", "обновить значение элемента по id");
        commands.put("remove_by_id", "удалить элемент с заданным ID");
        commands.put("clear", "очистить коллекцию");
        commands.put("execute_script", "считать и исполнить скрипт из указанного файла");
        commands.put("exit", "завершить программу");
        commands.put("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        commands.put("remove_greater", "удалить все элементы коллекции, значение которых выше значения нового элемента");
        commands.put("remove_lower", "удалить все элементы коллекции, значение которых ниже значения нового элемента");
        commands.put("average_of_minimal_point", "вывести среднее значение минимального балла");
        commands.put("filter_starts_with_name", "вывести элементы, значение поля которых начинается с заданной подстроки");
        commands.put("print_descending", "вывести элементы коллекции в порядке убывания");
        return commands;
    }

    public static HashMap<String, Command> argCheckMap(){

        HashMap<String, Command> commands = new HashMap<String, Command>();
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("add", new Add());
        commands.put("update_by_id", new UpdateById());
        commands.put("remove_by_id", new RemoveById());
        commands.put("clear", new Clear());
        commands.put("add_if_max", new AddIfMax());
        commands.put("remove_greater", new RemoveGreater());
        commands.put("remove_lower", new RemoveLower());
        commands.put("average_of_minimal_point", new AverageOfMinimalPoint());
        commands.put("filter_starts_with_name", new FilterStartsWithName());
        commands.put("print_descending", new PrintDescending());
        return commands;
    }
}
