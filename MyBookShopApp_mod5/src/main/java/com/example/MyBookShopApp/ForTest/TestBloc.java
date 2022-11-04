package com.example.MyBookShopApp.ForTest;

import com.example.MyBookShopApp.dataModele.Author;
import com.example.MyBookShopApp.dataModele.Book;
import com.example.MyBookShopApp.dataService.AuthorService;
import com.example.MyBookShopApp.dataService.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestBloc implements CommandLineRunner {
    @Autowired
    TestRepo repo;

    @Autowired
    BookService bookData;

    @Autowired
    AuthorService authorData;
    String sql="" +
            "insert into authors (first_name, last_name) values\n" +
            "('Carlos', 'Dodshon'),\n" +
            "('Jelene', 'Hopfner'),\n" +
            "('Dannye', 'Wickendon'),\n" +
            "('Alec', 'Shurrock'),\n" +
            "('Nadia', 'Brabham'),\n" +
            "('Silvie', 'Gateland'),\n" +
            "('Sharai', 'Feighney'),\n" +
            "('Andres', 'Djorevic'),\n" +
            "('Deeanne', 'Rivitt'),\n" +
            "('Javier', 'MacCallister'),\n" +
            "('Ami', 'Ramsbotham'),\n" +
            "('Georges', 'Smiley'),\n" +
            "('Nichol', 'Brick'),\n" +
            "('Dorris', 'Eastup'),\n" +
            "('Ewen', 'Chrestien'),\n" +
            "('Allin', 'Gilluley'),\n" +
            "('Verney', 'Lambdon'),\n" +
            "('Margalit', 'Mitten'),\n" +
            "('Rhonda', 'Peterson'),\n" +
            "('Melinde', 'Badsey'),\n" +
            "('Alexander', 'Cherrington');";
    private void insertAuthor(){
        List<Author> authorList=new ArrayList<>();
        Author author=new Author();
        author.setFirstName("Carlos");
        author.setLastName("Dodshon");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        author=new Author();
        author.setFirstName("Jelene");
        author.setLastName("Hopfner");
        authorList.add(author);
        authorData.addAllAuthors(authorList);
    }

    private void insertBooK(){
        insertAuthor();
        List<Author>authors=authorData.getAllAuthors();
        Book book=new Book();
        book.setAuthor(authors.get(0));
        book.setPrice("100");
        book.setPriceOld("old100");
        book.setTitle("TitleOne");
        List<Book>books=new ArrayList<>();
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        book=new Book();
        book.setAuthor(authors.get(1));
        book.setPrice("101");
        book.setPriceOld("old101");
        book.setTitle("TitleTow");
        books.add(book);
        bookData.addAllBooks(books);

    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println(sql);
        insertBooK();

//        repo.createOrUpdateOrDelete(sql);
    }
}
