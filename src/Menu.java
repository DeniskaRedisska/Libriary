import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {
    public static void initialization() {
        Library library = new Library();
        Admin admin = new Admin("deniskaarediskaa@gmail.com", "Lordofrings_by");
        try {
            library.writeUser(admin);
            while (true) {
                System.out.println("signUp:1\nsignIn:2\nexit:3");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        library.signUp();
                        break;
                    case 2:
                        User user = library.signIn();
                        if (user.getClass() == Admin.class) {
                            Menu.adminMenu((Admin) user);
                            System.out.println("admin");
                        }
                        if (user.getClass() == Client.class) {
                            System.out.println("user");
                            Menu.clientMenu((Client) user);
                        }
                        break;
                    case 3:
                        return;
                }
            }
            }
        catch (IOException | BadPaddingException | IllegalBlockSizeException |
                InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }


    public static void adminMenu(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("choose options:");
            System.out.println("1:read");
            System.out.println("2:add");
            System.out.println("3:search");
            System.out.println("4:delete");
            System.out.println("5:log out");
            choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1:
                        admin.read();
                        break;
                    case 2:
                        admin.addBook();
                        break;
                    case 3:
                        admin.searchFromFile();
                        break;
                    case 4:
                        admin.delete();
                        break;
                    case 5:
                        return;
                }
            } catch (IOException | MessagingException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void clientMenu(Client client) {

        int choice;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("choose options:");
            System.out.println("1:read");
            System.out.println("2:suggest book");
            System.out.println("3:search");
            System.out.println("4:log out");
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        client.read();
                        break;

                    case 2:
                        client.suggestBook();
                        break;

                    case 3:
                        client.search();
                        break;
                    case 4:
                        return;
                }
            } catch (IOException | MessagingException  e) {
                System.err.println(e.getMessage());
            }
            catch (NoSuchElementException e){//TODO exceptions
                System.err.println(e.getMessage());
                scanner.close();
            }
        }
    }
}
