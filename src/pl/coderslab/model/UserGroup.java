package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//CREATE TABLE user_group(id int AUTO_INCREMENT, name varchar(255), PRIMARY KEY(id)) default character set utf8 collate utf8_polish_ci;

public class UserGroup {
    int id;
    String name;

    public UserGroup() {
    }

    public UserGroup (String name) {
        this.name=name;
    }
// id tylko getter - setter nipotrzebny bo BD ma auto_increment;
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO user_group(name) VALUES (?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE user_group SET name=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }
    }
    static public UserGroup loadUserGroupById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM user_group where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            UserGroup loadedUserGroup = new UserGroup();
            loadedUserGroup.id = resultSet.getInt("id");
            loadedUserGroup.name = resultSet.getString("name");
            return loadedUserGroup;}
        return null;}

    static public UserGroup[] loadAllUserGroups (Connection conn) throws SQLException {
        ArrayList<UserGroup> userGroups = new ArrayList<UserGroup>();
        String sql = "SELECT * FROM user_group";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserGroup loadedUserGroup = new UserGroup();
            loadedUserGroup.id = resultSet.getInt("id");
            loadedUserGroup.name = resultSet.getString("name");
            userGroups.add(loadedUserGroup);} // dodaj następnego do listy.
        UserGroup[] uGArray = new UserGroup[userGroups.size()];
        uGArray = userGroups.toArray(uGArray);
        return uGArray;}

  // usuń userGroup zBD
    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM user_group WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }

    }

        public String toString() {
            return "User Group{" +
                    "id=" + id +
                    "}, Group name='" + name +
                    "'}";
        }


} // ## Class bracket.


