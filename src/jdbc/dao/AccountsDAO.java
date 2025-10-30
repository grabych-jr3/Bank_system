package jdbc.dao;

import bank.management.system.model.Account;
import bank.management.system.model.User;

import java.util.List;

public interface AccountsDAO {
    public Account createAccount(User user, Account account);
    public List<Account> getAllAccounts(User user);
    public void deposit(int accountId, double value);
    public void withdraw(int accountId, double value);
    public void deleteAccount(int accountId);
}
