package pl.coderslab.model;

import pl.coderslab.util.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
// pola z tabeli BD
    private int id;
    private String userName;
    private String email;
    private String password;
    private UserGroup userGroup;

// pusty konstruktor;
    public User() {
    }
// konstruktor
    public User(String userName, String email, String password, UserGroup userGroup) {
        this.userName = userName;
        this.email = email;
        setPassword(password);
        this.userGroup = userGroup;
    }

// gettery i settery
    public int getId() {
        return id;
    }

    public String getName() {
        return userName;
    }
    public void setName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public UserGroup getGroup() {
        return userGroup;
    }
    public void setGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

// Zapisz do  BD zapisuje nowy element do BD ub zmodyfikowany element. Poznajmy po id==0
    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO users(username, email, password, user_group_id) VALUES (?, ?, ?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.userName);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.setInt(4, this.userGroup.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE users SET username=?, email=?, password=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.userName);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.setInt(4, this.userGroup.getId()); // pobieramy z pola GR ident GRid
            preparedStatement.executeUpdate();
        }
    }

// Wczytaj 1 user po id. Metoda statyczna dlatego przekzujemy dodatkowo id
    static public User loadUserById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM users where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.userName = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            int usrGroup = resultSet.getInt("user_group_id"); // z user pobranie nr grupy
            loadedUser.userGroup = UserGroup.loadUserGroupById(conn,usrGroup);// z user group wczytanie całej grupy
            return loadedUser;}
        return null;}

    // Wczytaj wszystkich z BD
    static public User[] loadAllUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.userName = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            int usrGoup = resultSet.getInt("user_group_id");        // z user pobranie nr grupy
            loadedUser.userGroup = UserGroup.loadUserGroupById(conn, usrGoup);   // z user group wczytanie całej grupy
        }
        User[] uArray = new User[users.size()]; uArray = users.toArray(uArray);
        return uArray;}

// usuń usera zBD
    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "}, userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
