package utils;

import Collection.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс отвечающий за сохранение и загрузку коллекции из файла
 */
public class FileManager {
    private String filePath;

    public FileManager(){
        this.filePath  = "data.csv";
    }

    public void writeToCSV(CollectionHandler collectionHandler) throws IOException{
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            BufferedOutputStream bos = new BufferedOutputStream(out);
            for (LabWork labWork : collectionHandler.getCollection()) {
                String labWorkStr = labWork.getId() + ", " + labWork.getName() + ", "
                        + labWork.getCoordinates().getX() + ", " + labWork.getCoordinates().getY() + ", "
                        + labWork.getCreationDate() + ", " + labWork.getMinimalPoint() + ", "
                        + labWork.getDifficulty() + ", " + labWork.getDiscipline().getName();
                System.out.println(labWorkStr);
                byte[] buffer = labWorkStr.getBytes();
                bos.write(buffer, 0, buffer.length);
                bos.write('\n');
                bos.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            try (FileOutputStream out = new FileOutputStream("dataReserved.csv")) {
                BufferedOutputStream bos = new BufferedOutputStream(out);
                for (LabWork labWork : collectionHandler.getCollection()) {
                    // example of output:  "Lab-1, 1, 1.0, 6, HARD, OPD"
                    String labWorkStr = labWork.getId() + ", " + labWork.getName() + ", "
                            + labWork.getCoordinates().getX() + ", " + labWork.getCoordinates().getY() + ", "
                            + labWork.getCreationDate() + ", " + labWork.getMinimalPoint() + ", "
                            + labWork.getDifficulty() + ", " + labWork.getDiscipline().getName();

                    System.out.println(labWorkStr);
                    byte[] buffer = labWorkStr.getBytes();
                    bos.write(buffer, 0, buffer.length);
                    bos.write('\n');
                    bos.flush();
                }
            } catch (IOException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

    public  LabWork[] readFromFile() throws IOException {
        try{
            File file = new File(filePath);
            Scanner input = new Scanner(file);
            String[] arrayForSplit = new String[1001];
            String line;
            List<LabWork> labWorks = new ArrayList<LabWork>();
            while (input.hasNextLine()) {
                line = input.nextLine();
                arrayForSplit = line.split(", ");
                Date date = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH).parse(arrayForSplit[4]);
                LabWork lab = new LabWork(Integer.parseInt(arrayForSplit[0]), arrayForSplit[1], new Coordinates(Long.parseLong(arrayForSplit[2]), Float.parseFloat(arrayForSplit[3])), date, Long.parseLong(arrayForSplit[5]), Difficulty.valueOf(arrayForSplit[6]), new Discipline(arrayForSplit[7]), arrayForSplit[8]);
                labWorks.add(lab);
            }
            LabWork[] labWorkArray = labWorks.toArray(new LabWork[labWorks.size()]);
            return labWorkArray;

        } catch (NullPointerException npe) {
            IOHandler.println("Ошибка чтения из файла.");
            System.exit(1);
            LabWork[] labWorkList = new LabWork[1];
            return labWorkList;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
