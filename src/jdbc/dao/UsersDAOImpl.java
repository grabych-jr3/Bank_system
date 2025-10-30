package jdbc.dao;

import bank.management.system.model.User;
import exceptions.LoginException;
import jdbc.DBConnection;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAOImpl implements UsersDAO {

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO users(login, password, full_name) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());

            ps.executeUpdate();

            // Отримуємо згенерований ключ (id)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    user.setId(generatedId);
                }
            }

            // Тепер підтягнемо created_at (якщо він є у таблиці)
            String selectSql = "SELECT created_at FROM users WHERE id = ?";
            try (PreparedStatement ps2 = con.prepareStatement(selectSql)) {
                ps2.setInt(1, user.getId());
                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) {
                        user.setCreatedAt(rs2.getTimestamp("created_at").toString());
                    }
                }
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUser(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setCreatedAt(rs.getString("created_at"));
                return user;
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void checkLogin(String login){
        String sql = "SELECT 1 FROM users WHERE login = ?";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                throw new LoginException("This login is already taken!");
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
