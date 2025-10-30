package bank.management.system.menu;

import bank.management.system.model.User;
import bank.management.system.services.AccountService;
import java.util.Scanner;

public class UserMenu {
    private final User user;
    private final AccountService accountService = new AccountService();
    private final Scanner scan = new Scanner(System.in);

    public UserMenu(User user){
        this.user = user;
    }

    public void start(){
        while(true){
            System.out.println("1. Create new account");
            System.out.println("2. Print all accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Delete account");
            System.out.println("6. Exit");
            System.out.print("Your choice: ");
            int choice = Integer.parseInt(scan.nextLine());

            switch (choice) {
                case 1 -> accountService.createAccount(user);
                case 2 -> accountService.getAllAccounts(user);
                case 3 -> accountService.depositMoney(user);
                case 4 -> accountService.withdrawMoney(user);
                case 5 -> accountService.deleteAccount(user);
                case 6 -> {
                    System.out.println("Good Bye!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

}
