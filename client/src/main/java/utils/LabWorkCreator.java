package utils;

import Collection.Coordinates;
import Collection.Difficulty;
import Collection.Discipline;
import Collection.LabWork;
import exceptions.ValueException;
import Collection.User;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LabWorkCreator {
    Scanner scanner;
    public LabWorkCreator(){
        scanner = new Scanner(System.in);
    }
    /**
     * Если введенная строка будет пустой будет кинуто исключение ValueException
     * @see ValueException
     * @return name
     */
    public String nameCreate(){
        String name;
        try{
            IOHandler.println("Введите название лабораторной работы: ");
            name = scanner.nextLine();
            if(name.trim().isEmpty()) throw new ValueException();
        }catch (InputMismatchException e){
            IOHandler.println("Неправильный формат введенных данных, попробуйте еще раз.");
            name = nameCreate();
        }catch (ValueException ve){
            IOHandler.println("Название не может быть пустым");
            name = nameCreate();
        }
        return name;
    }
    /**
     * Если введенные значения будут не подходить по данным ограничениям будет кинуто исключение ValueException
     * @see ValueException
     * @return Collection.Coordinates
     */
    public Coordinates coordinatesCreate(){
        long x=0;
        float y=0;
        Coordinates coordinates = null;
        try{
            IOHandler.println("Введите координату X в типе данных Long(больше -1000): ");
            x = Long.parseLong(scanner.nextLine().trim());
            IOHandler.println("Введите координату Y в типе данных Float(больше -512): ");
            y = Long.parseLong(scanner.nextLine().trim());
            if(x < -999 || y < -511) throw new ValueException();
            coordinates = new Coordinates(x, y);
        }catch (InputMismatchException e){
            IOHandler.println("Неправильный формат введенных данных, попробуйте еще раз.");
            scanner.nextLine();
            coordinates = coordinatesCreate();
        }catch (ValueException ve){
            IOHandler.println("Долгота должна быть больше -1000, широта больше -512");
            coordinates = coordinatesCreate();
        }catch (NumberFormatException nfe){
            IOHandler.println("Неправильный формат введенных данных, попробуйте еще раз.");
            coordinates = coordinatesCreate();
        }
        return coordinates;
    }

    public Long minimalPointCreate(){
        long point = 6;
        try{
            IOHandler.println("Введите минимальный балл за эту работу [6;20]: ");
            point = Long.parseLong(scanner.nextLine().trim());
            if(point < 6 || point > 20) throw new ValueException();
        }catch (InputMismatchException e){
            IOHandler.println("Неправильный формат введенных данных, попробуйте еще раз.");
            scanner.nextLine();
            point = minimalPointCreate();
        }catch (ValueException ve){
            IOHandler.println("Минимальный балл должен быть в границах [6;20]");
            point = minimalPointCreate();
        }catch (NumberFormatException nfe){
            IOHandler.println("Неправильный формат введенных данных, попробуйте еще раз.");
            point = minimalPointCreate();
        }
        return point;
    }

    /**
     * @return Collection.Difficulty
     */
    public Difficulty difficultyChoose(){
        Difficulty difficulty = null;

        try{
            IOHandler.println("Введите сложность(HARD, VERY_HARD, IMPOSSIBLE, INSANE, HOPELESS):");
            difficulty = difficulty.valueOf(scanner.nextLine().toUpperCase().trim());
        }catch (IllegalArgumentException e){
            IOHandler.println("Такой сложности не существует!");
            difficulty = difficultyChoose();
        }
        return difficulty;
    }

    /**
     * @return Collection.Discipline
     */
    public Discipline disciplineChoose(){
        Discipline discipline = new Discipline(null);

        IOHandler.println("Введите дисциплину: ");
        String disciplineIn = scanner.nextLine();
        if (!(disciplineIn.equals(""))) {
            discipline.setName(disciplineIn);
        }
        return discipline;
    }

    public LabWork labWorkCreate(User user){
        return new LabWork(0, nameCreate(), coordinatesCreate(), new Date(), minimalPointCreate(), difficultyChoose(), disciplineChoose(), user.getLogin());
    }
}
