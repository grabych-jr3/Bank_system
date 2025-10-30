package jdbc.dao;

import bank.management.system.model.Account;
import bank.management.system.model.User;
import exceptions.InsufficientFundsException;
import jdbc.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDAOImpl implements AccountsDAO{

    @Override
    public Account createAccount(User user, Account account) {
        String sql = "INSERT INTO accounts(user_id, account) VALUES(?, ?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, user.getId());
            ps.setString(2, account.getAccount());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    int generated_id = rs.getInt(1);
                    account.setAccountId(generated_id);
                }
            }

            String sql2 = "SELECT * FROM accounts WHERE account_id = ?";
            try(PreparedStatement ps2 = con.prepareStatement(sql2)){
                ps2.setInt(1, account.getAccountId());

                try(ResultSet rs2 = ps2.executeQuery()){
                    if(rs2.next()){
                        account.setUserId(rs2.getInt("user_id"));
                        account.setCurrency(rs2.getString("currency"));
                        account.setBalance(rs2.getDouble("balance"));
                        account.setCreatedAt(rs2.getTimestamp("created_at").toString());
                    }
                }
            }

            return account;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> getAllAccounts(User user){
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                Account acc = new Account();
                acc.setAccountId(rs.getInt("account_id"));
                acc.setUserId(rs.getInt("user_id"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setCurrency(rs.getString("currency"));
                acc.setAccount(rs.getString("account"));
                acc.setCreatedAt(rs.getTimestamp("created_at").toString());

                accounts.add(acc);
            }
        }catch (SQLException e){
            System.out.println("ERROR");
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public void deposit(int accountId, double value){
        String sql = "SELECT balance FROM accounts WHERE account_id = ?";
        String sql2 = "UPDATE accounts SET balance = ? WHERE account_id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps2 = con.prepareStatement(sql2)){

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            double currentBalance = 0;
            if(rs.next()){
                currentBalance = rs.getDouble("balance");
            }

            double newBalance = currentBalance + value;
            ps2.setDouble(1, newBalance);
            ps2.setInt(2, accountId);
            ps2.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void withdraw(int id, double value){
        String sql = "SELECT balance FROM accounts WHERE account_id = ?";
        String sql2 = "UPDATE accounts SET balance = ? WHERE account_id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps2 = con.prepareStatement(sql2)){

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            double currentBalance = 0;
            if(rs.next()){
                currentBalance = rs.getDouble("balance");
            }

            if(currentBalance < value){
                throw new InsufficientFundsException("Your balance is too low!");
            }

            currentBalance -= value;
            ps2.setDouble(1, currentBalance);
            ps2.setInt(2, id);
            ps2.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(int accountId){
        String sql = "DELETE FROM accounts WHERE account_id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, accountId);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
