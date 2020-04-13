public class Book {
    private String name;
    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean equals(Book obj) {
        if(name.equals(obj.name) && author.equals(obj.author)){
        return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return name + " " + author;
    }
}
