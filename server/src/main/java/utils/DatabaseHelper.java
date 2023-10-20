package utils;

import Collection.Coordinates;
import Collection.Difficulty;
import Collection.Discipline;
import Collection.LabWork;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;


public class DatabaseHelper {

    Connection conn = null;
    String user = "s368925";
    String password = "5mCSFOGOnfrbfJeC";
    String url = "jdbc:postgresql://localhost:5432/studs";
    private Logger logger = Logger.getLogger("logger");
    public static final Set<Long> IDs = new LinkedHashSet<>();


    public Connection connect(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            logger.info("Connected to the PostgreSQL server successfully.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public int saveLabWork(LabWork labWork){
        int generatedId = (int) generateID();
        if(!labWork.getSaved()){
            try{
                Connection conn = connect();
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO " +   "LabWork " +
                                "(name, coordinates_x, coordinates_y, creation_date, minimalPoint, difficulty, Discipline, creator, id) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, labWork.getName());
                statement.setLong(2, labWork.getCoordinates().getX());
                statement.setFloat(3, labWork.getCoordinates().getY());
                Date creationDate = labWork.getCreationDate();
                SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                statement.setTimestamp(4, Timestamp.valueOf(sm.format(creationDate)));
                statement.setLong(5, labWork.getMinimalPoint());
                statement.setObject(6, labWork.getDifficulty(), Types.OTHER);
                statement.setObject(7, labWork.getDiscipline().getName(), Types.OTHER);
                statement.setString(8, labWork.getCreator());
                statement.setInt(9, generatedId);

                labWork.setSaved();
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Inserting person failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return generatedId;
    }

    public TreeSet<LabWork> getAllLabWorks() throws SQLException {
        Connection conn = connect();
        TreeSet<LabWork> labWorks = new TreeSet<LabWork>();
        final String SELECT_ALL_LABWORKS = "SELECT * FROM labwork";
        PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_LABWORKS); {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Long coordinates_x = resultSet.getLong("coordinates_x");
                Float coordinates_y = resultSet.getFloat("coordinates_y");
                Coordinates coordinates = new Coordinates(coordinates_x, coordinates_y);
                Date creationDate = null;
                try {
                    creationDate = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH).parse(resultSet.getTimestamp("creation_date").toString());
                } catch (ParseException e) {
                    creationDate = new Date();
                }
                Long minimalPoint = resultSet.getLong("minimalPoint");
                Difficulty difficulty = Difficulty.valueOf(resultSet.getString("difficulty"));
                Discipline discipline = new Discipline(resultSet.getString("discipline"));
                String creator = resultSet.getString("creator");

                LabWork labWork = new LabWork(id, name, coordinates, creationDate, minimalPoint, difficulty, discipline, creator);
                labWork.setSaved();
                labWorks.add(labWork);
            }
        }
        return labWorks;
    }

    public void deleteLabWork(int id){
        try{
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM LabWork WHERE id = ?");

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting person failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void register(String login, String pwd) throws SQLException{
        Connection conn = connect();
        PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO users" +
                        "(login, password)" +
                        "VALUES (?, ?)");

        HasherHelper hasherHelper = new HasherHelper("SHA-256");
        statement.setString(1, login);
        statement.setString(2, hasherHelper.encode(pwd));

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Registring failed, no rows affected.");
        }
    }

    public boolean checkIfUserExists(String login, String pwd){
        try{
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            statement.setString(1, login);
            statement.setString(2, pwd);

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }
    public long generateNextId(){
        Long randomID = 0l;
        while (true) {
            randomID = generateID();
            if (checkIfIDUnique(randomID)) {
                saveId(randomID);
                return Math.toIntExact(randomID);
            }
        }
    }
    public static boolean checkIfIDUnique(long id) {
        return !IDs.contains(id);
    }
    public static long generateID() {
        Random rand = new Random();
        int upperbound = 999999;
        int int_random = rand.nextInt(upperbound);
        return int_random;
    }
    public static void saveId(long id) {
        IDs.add(id);
    }
}




