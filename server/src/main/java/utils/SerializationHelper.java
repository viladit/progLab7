package utils;

import Collection.LabWork;

import java.io.*;
import java.util.List;
import java.util.TreeSet;

import com.opencsv.CSVWriter;


public class SerializationHelper {

    public String serializeToCSV(CollectionHandler collectionHandler) {
        TreeSet<LabWork> labWorkList = collectionHandler.getCollection();

        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter)) {

            // Записываем заголовки (если нужно)
            String[] header = {"name", "age"};
            csvWriter.writeNext(header);

            // Записываем данные
            for (LabWork labWork : labWorkList) {
                String[] data = {String.valueOf(labWork.getId()), String.valueOf(labWork.getCoordinates().getX()), String.valueOf(labWork.getCoordinates().getY()), String.valueOf(labWork.getCreationDate()), String.valueOf(labWork.getMinimalPoint()), String.valueOf(labWork.getDifficulty()), String.valueOf(labWork.getDiscipline()), labWork.getCreator()};
                csvWriter.writeNext(data);
            }

            String csvString = stringWriter.toString();
            System.out.println("CSV строка:\n" + csvString);
            return csvString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
