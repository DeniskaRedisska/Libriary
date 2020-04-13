import java.util.Scanner;

public abstract class User {
    protected String email;
    protected String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    protected Book getBook(){
        Scanner input = new Scanner(System.in);//TODO in func that returns Book
        String author = input.nextLine();
        String name = input.nextLine();
        return new Book(author, name);
    }
}
