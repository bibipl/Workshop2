package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Solution {
// CREATE TABLE solution(id int AUTO_INCREMENT PRIMARY KEY, created DATETIME, updated DATETIME, description text, exercise_id int NOT NULL, users_id BIGINT(20)
// NOT NULL,FOREIGN KEY(exercise_id) REFERENCES exercise(id), FOREIGN KEY(users_id) REFERENCES users(id));

    // pola z tabeli BD
    private int id;
    private Date created;
    private Date updated;
    private String description;
    private int excerciseId;
    private int usersId;

    public Solution() {
    }

    public Solution(Date created, Date updated, String description, int excercise_id, int usersId) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.excerciseId = excercise_id;
        this.usersId = usersId;
    }

    public int getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getExcercise_id() {
        return excerciseId;
    }
    public void setExcercise_id(int excercise_id) {
        this.excerciseId = excercise_id;
    }

    public int getUsersId() {
        return usersId;
    }
    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    // Zapisz do  BD zapisuje nowy element do BD ub zmodyfikowany element. Poznajmy po id==0
    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(created, updated, description, exerciseId, usersId) VALUES (?, ?, ?, ?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.excerciseId);
            preparedStatement.setInt(5, this.usersId);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET created=?, updated=?, description=?, exercise_id=?, users_id=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.excerciseId); // pobieramy z pola GR ident GRid
            preparedStatement.setInt(5, this.usersId); // pobieramy z pola GR ident GRid
            preparedStatement.setInt(6, this.id); // pobieramy z pola GR ident GRid
            preparedStatement.executeUpdate();
        }
    }

    // Wczytaj 1 solution po id. Metoda statyczna dlatego przekzujemy dodatkowo id
    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.excerciseId = resultSet.getInt("exercise_id");
            loadedSolution.usersId = resultSet.getInt("users_id");
            return loadedSolution;}
        return null;}

    // Wczytaj wszystkich z BD
    static public Solution[] loadAllsolutions(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.excerciseId = resultSet.getInt("exercise_id");
            loadedSolution.usersId = resultSet.getInt("users_id");
            solutions.add(loadedSolution);
        }
        Solution[] solutionArray = new Solution[solutions.size()];
        solutionArray = solutions.toArray(solutionArray);
        return solutionArray;}

    // usuń exercise zBD
    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                "}, created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                ", exercise_id ='"+ excerciseId + '\'' +
                ", users_id ='"+usersId + '\'' +
                '}';
    }



} // last class bracket
