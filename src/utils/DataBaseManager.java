package utils;

import network.client.User;
import сollection.SpaceMarine;
import сollection.SpaceStorage;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class DataBaseManager {
    //    private static final String DB_URL = "jdbc:postgresql://pg:5432/studs";
    private ObjectInputStream input;
    private ByteArrayInputStream byteInput;
    private ByteArrayOutputStream byteOutput;
    private ObjectOutputStream output;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static String USER = "postgres";
    private static String PASS = "argsf031";
    private static final String TABLE_NAME = "collection";
    private static final String USERS_TABLE = "users";
    private Connection connection;
    private PassEncoder passEncoder;
    private String pepper = "af56a";

    static {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите логин и пароль");
//        USER = scanner.nextLine().trim();
//        PASS = scanner.nextLine().trim();
        System.out.println("Connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver successfully connected");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path");
            e.printStackTrace();
        }
    }


    public DataBaseManager(String dataBaseUrl, String user, String pass) throws IOException {
        passEncoder = new PassEncoder(pepper);
        byteOutput = new ByteArrayOutputStream();
        byteInput = new ByteArrayInputStream(byteOutput.toByteArray());
        output = new ObjectOutputStream(byteOutput);
        try {
            connection = DriverManager.getConnection(dataBaseUrl, user, pass);
            System.out.println("Connection to database successful");
//            PreparedStatement statement = connection.prepareStatement(sqlInstallCommit);
//            statement.execute();
        } catch (SQLException e) {
            System.out.println("Connection to database failed");
//            e.printStackTrace();
        }
    }

    public DataBaseManager(String address, int port, String dbName, String user, String pass) throws IOException {
        this("jdbc:postgresql://" + address + ":" + port + "/" + dbName, user, pass);
    }

    public DataBaseManager() throws IOException {
        this(DB_URL, USER, PASS);
    }

    public boolean addUser(User user) {
        String salt = new RandomStringGenerator().generate(6, 6);
        String hash = passEncoder.getHash(user.getPass() + salt);
        try {
            PreparedStatement statement = connection.prepareStatement("insert into " + USERS_TABLE + " values (?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            statement.setString(3, salt);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean containsUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + USERS_TABLE + " where name = ?");
            statement.setString(1, user.getName());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return false;
            String salt = resultSet.getString(3);
            String hash = passEncoder.getHash(user.getPass() + salt);
            statement = connection.prepareStatement("select * from " + USERS_TABLE + " where name = ? and password = ? and salt=?");
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            statement.setString(3, salt);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //    String sqlInstallCommit =  "CREATE TABLE collection ( id bigint PRIMARY KEY, owner CHARACTER VARYING(40), marine BYTEA, key CHARACTER VARYING(50) ); CREATE TABLE users ( name CHARACTER VARYING(40) PRIMARY KEY, password CHARACTER VARYING(300), salt CHARACTER VARYING(20) ); CREATE SEQUENCE ids;";
    public Connection getConnection() {
        return connection;
    }

    public boolean insertSpaceMarine(String key, SpaceMarine marine) {

        try {
            long id = generateId();
            marine.setId((int) id);
            byte[] bytes = mariteToBytes(marine);
            PreparedStatement statement = connection.prepareStatement("insert into " + TABLE_NAME + " values (?, ?, ?, ?)");
            statement.setInt(1, (int) id);
            statement.setString(2, marine.getUserName());
            statement.setBinaryStream(3, new ByteArrayInputStream(bytes), bytes.length);
            statement.setString(4, key);
            statement.execute();
        } catch (SQLException | IOException exp) {
            exp.printStackTrace();
            System.out.println("Не удалось вставить элемент в базу данных");
            return false;
        }
        return true;

    }

    public boolean insertSpaceMarine(String key, SpaceMarine marine, int id) {

        try {

            marine.setId((int) id);
            byte[] bytes = mariteToBytes(marine);
            PreparedStatement statement = connection.prepareStatement("insert into " + TABLE_NAME + " values (?, ?, ?, ?)");
            statement.setInt(1, (int) id);
            statement.setString(2, marine.getUserName());
            statement.setBinaryStream(3, new ByteArrayInputStream(bytes), bytes.length);
            statement.setString(4, key);
            statement.execute();
        } catch (SQLException | IOException exp) {
            exp.printStackTrace();
            System.out.println("Не удалось вставить элемент в базу данных");
            return false;
        }
        return true;

    }

    public SpaceStorage getCollection() {
        SpaceStorage spaceStorage = new SpaceStorage();
        try (PreparedStatement statement = connection.prepareStatement("select * from " + TABLE_NAME);) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String owner = resultSet.getString(2);
                SpaceMarine marine = bytesToSpaceMarine(resultSet.getBytes(3));
                marine.setId((int) id);
                marine.setUser(owner);
                String key = resultSet.getString(4);
                spaceStorage.put(key, marine);

            }
        } catch (SQLException | IOException | ClassNotFoundException exp) {
            exp.printStackTrace();
        } catch (NullPointerException exp) {
            System.out.println("БД не доступна");
            System.exit(0);
        }
        return spaceStorage;
    }

    public long generateId() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select nextval('ids')");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    public boolean removeAll(String userName) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from " + TABLE_NAME + " where owner=?");
            statement.setString(1, userName);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(long id, String key, SpaceMarine marine)  {
        try {
            PreparedStatement statement = connection.prepareStatement("select from " + TABLE_NAME + " where id=?");
            statement.setInt(1, (int) id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                removeByKey(key);
                insertSpaceMarine(key, marine, (int) id);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int update(long id, String key) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "update " + TABLE_NAME + " set key=? where id = ?");
            statement.setString(1, key);
            statement.setInt(2, (int) id);
            statement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private byte[] mariteToBytes(SpaceMarine marine) throws IOException {
        output = new ObjectOutputStream(byteOutput);
        output.writeObject(marine);
        byte[] bytes = byteOutput.toByteArray();
        output.flush();
        output.reset();
        byteOutput = new ByteArrayOutputStream();
        return bytes;
    }

    private SpaceMarine bytesToSpaceMarine(byte[] bytes) throws IOException, ClassNotFoundException {
        byteInput = new ByteArrayInputStream(bytes);
        input = new ObjectInputStream(byteInput);
        try {
            SpaceMarine marine = (SpaceMarine) input.readObject();
            return marine;
        } catch (InvalidClassException exp) {
            exp.printStackTrace();
        }
        catch (EOFException exp) {

        }
        return null;
    }

    public int remove(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from " + TABLE_NAME + " where id=?");
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int removeByKey(String key) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from " + TABLE_NAME + " where key=?");
            statement.setString(1, key);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

