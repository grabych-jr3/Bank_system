package bank.management.system.menu;

import bank.management.system.model.User;
import bank.management.system.services.UserService;
import exceptions.LoginException;

import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("1. Log in");
        System.out.println("2. Create new account");
        System.out.print("Your choice: ");
        int enter = Integer.parseInt(scan.nextLine());

        try{
            User user;
            if(enter == 1){
                System.out.print("Enter your login: ");
                String l = scan.nextLine();
                System.out.print("Enter your password: ");
                String p = scan.nextLine();

                user = userService.login(l, p);
                System.out.println("Logged in successfully!");
            } else if (enter == 2) {
                System.out.println("Create login: ");
                String l = scan.nextLine();
                System.out.println("Create password: ");
                String p  = scan.nextLine();
                System.out.println("Enter your full name: ");
                String fN = scan.nextLine();

                user = userService.register(l, p, fN);
                System.out.println("Account was created successfully");
            }else{
                System.out.println("Invalid choice!");
                return;
            }

            UserMenu userMenu = new UserMenu(user);
            userMenu.start();
        }catch (LoginException e){
            System.out.println(e.getMessage());
        } catch (ArithmeticException e){
            System.out.println(e.getMessage());
        }


    }
}
