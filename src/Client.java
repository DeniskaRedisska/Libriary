import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client extends User implements SendingEmail {
    private Library lib;

    public Client() {
        lib = new Library();
    }

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
        lib = new Library();
    }

    public void read() throws IOException {
        lib.show();
    }

    public void search() throws IOException {
        Book book = getBook();
        lib.searchFromFile(book);
    }

    public void suggestBook() throws IOException, MessagingException {
        Book book = getBook();
        Path path = Paths.get(lib.getUSER_INFO_PATH());
        Scanner scanner = new Scanner(path);
        boolean isAdmin = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] elements = line.split("[-\\n]");
            String to = "";
            for (String elem : elements) {
                if (elem.contains("@")) {
                    to = elem;
                }
                if (elem.equals("true")) {
                    isAdmin = true;
                }
            }
            if (isAdmin)
                sendEmail(this.email, to, this.password, book.toString());
            isAdmin = false;
        }
    }
}
