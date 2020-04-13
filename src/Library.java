import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Library {
    private final String LIBRARY_PATH = "BooksCollection.txt";
    private final String USER_INFO_PATH = "UserInfo.txt";

    public String getLIBRARY_PATH() {
        return LIBRARY_PATH;
    }

    public String getUSER_INFO_PATH() {
        return USER_INFO_PATH;
    }

    private String clientOrAdmin(User user) {
        return user.getClass() == Admin.class ? "true" : "false";
    }

    public void writeUser(User user) throws IOException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        File newFile = new File(USER_INFO_PATH);
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, true));
        String str = user.getEmail() + "-" + Encryption.encrypt(user.getPassword()) + "-" + clientOrAdmin(user);
        writer.write(str + System.getProperty("line.separator"));
        writer.close();
    }

    public User signIn() throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException {
        boolean isExisting = false;
        boolean isAdmin = false;
        while (!isExisting) {
            Scanner scanner = new Scanner(System.in);
            String email = scanner.nextLine();
            String password = scanner.nextLine();

            Path path = Paths.get(USER_INFO_PATH);
            Scanner scannerFromFile = new Scanner(path);
            while (scannerFromFile.hasNextLine()) {
                String line = scannerFromFile.nextLine();
                String[] elements = line.split("-");
                if (elements[0].equals(email)) {
                    if (elements[1].equals(Encryption.encrypt(password))) {
                        isExisting = true;
                    }
                    if (elements[2].equals("true")) {
                        isAdmin = true;
                    }
                    break;
                }
            }
            if (!isExisting) System.out.println("Incorrect email or password");
        }
        if (isAdmin) {
            return new Admin();
        } else {
            return new Client();
        }
    }

    public void signUp() throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException,
            BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        String password = scanner.nextLine();//TODO regex check
        Client client = new Client(email, password);
        writeUser(client);
    }

    public Book searchFromFile(Book book) throws IOException {
        Path path = Paths.get(LIBRARY_PATH);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(book.toString())) {
                System.out.println(line);
                return book;
            }
        }
        scanner.close();
        System.out.println("no book");
        return null;
    }

    public void addBook(Book book) throws IOException, MessagingException {
        File newFile = new File(LIBRARY_PATH);
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, true));
        writer.write(book.toString() + System.getProperty("line.separator"));
        writer.close();
    }

    public void delete(Book book) throws IOException {
        File newFile = new File(LIBRARY_PATH);
        File tempFile = new File("TempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(newFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(book.toString())) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        Scanner scan = new Scanner(tempFile);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            System.out.println(line);
        }
        scan.close();

        newFile.delete();
        Path oldPath = Paths.get("TempFile.txt");
        Path newPath = Paths.get(LIBRARY_PATH);
        try {
            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(tempFile.getName());
    }

    public void show() throws IOException {
        Path path = Paths.get(LIBRARY_PATH);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
        scanner.close();
    }
}
