package bank.management.system.services;

import bank.management.system.model.Account;
import bank.management.system.model.User;
import exceptions.InsufficientFundsException;
import jdbc.dao.AccountsDAO;
import jdbc.dao.AccountsDAOImpl;

import java.util.List;
import java.util.Scanner;

public class AccountService {
    Scanner scan = new Scanner(System.in);
    private final AccountsDAO accountsDAO = new AccountsDAOImpl();

    public void createAccount(User user){
        Account acc = new Account();
        acc = accountsDAO.createAccount(user, acc);
        System.out.println("Account was created: " + acc);
    }

    public void getAllAccounts(User user){
        List<Account> accounts = accountsDAO.getAllAccounts(user);
        if(accounts.isEmpty()){
            System.out.println("No accounts found!");
            return;
        }
        for (Account acc : accounts) {
            System.out.println(acc);
        }
    }

    public void depositMoney(User user){
        List<Account> accounts = accountsDAO.getAllAccounts(user);
        printAccounts(accounts);

        System.out.print("Choose account id to deposit: ");
        int id = Integer.parseInt(scan.nextLine());

        System.out.print("Enter the amount: ");
        double amount = Double.parseDouble(scan.nextLine());

        accountsDAO.deposit(id, amount);
    }

    public void withdrawMoney(User user){
        List<Account> accounts = accountsDAO.getAllAccounts(user);
        printAccounts(accounts);

        System.out.print("Choose account id to withdraw: ");
        int id = Integer.parseInt(scan.nextLine());

        System.out.print("Enter the amount: ");
        double amount = Double.parseDouble(scan.nextLine());

        try{
            accountsDAO.withdraw(id, amount);
            System.out.println("Withdraw successful!");
        }catch (InsufficientFundsException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(User user){
        List<Account> accounts = accountsDAO.getAllAccounts(user);
        printAccounts(accounts);

        System.out.print("Choose account id to delete: ");
        int id = Integer.parseInt(scan.nextLine());

        accountsDAO.deleteAccount(id);
        System.out.println("Account was deleted!");
    }

    private void printAccounts(List<Account> accounts) {
        for (Account a : accounts) {
            System.out.println(a);
        }
    }

}
