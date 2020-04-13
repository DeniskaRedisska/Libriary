import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Admin extends User implements SendingEmail {
    private Library lib;

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
        lib = new Library();
    }

    public Admin() {
        lib = new Library();
    }

    public void addBook() throws IOException, MessagingException {
        Book book = getBook();
        Path path = Paths.get(lib.getUSER_INFO_PATH());
        Scanner scanner = new Scanner(path);
        boolean isClient = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] elements = line.split("-|\\n");
            String to = "";
            for (String elem : elements) {
                if (elem.contains("@")) {
                    to = elem;
                }
                if (elem.equals("false")) {
                    isClient = true;
                }
            }
            if (isClient)
                sendEmail("deniskaarediskaa@gmail.com", to, "********", book.toString());
            isClient = false;
        }
        lib.addBook(book);
    }


    public void read() throws IOException {
        lib.show();
    }

    public Book searchFromFile() throws IOException {
        Book book = getBook();
        return lib.searchFromFile(book);
    }

    public void delete() throws IOException {
        Book book = getBook();
        lib.delete(book);
    }
}
