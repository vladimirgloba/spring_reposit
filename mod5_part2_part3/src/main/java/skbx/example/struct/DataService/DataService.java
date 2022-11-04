package skbx.example.struct.DataService;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skbx.example.struct.AuthorsAndBooks.AuthorEntity;
import skbx.example.struct.AuthorsAndBooks.Book;
import skbx.example.struct.repository.AuthorRepository;
import skbx.example.struct.repository.BookRepository;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class DataService {
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    public List<Book> finedAllBooks() {
        return bookRepo.findAll();
    }

    public List<AuthorEntity> finedAllAuthors() {
        return authorRepo.findAll();
    }

    public void bookGenerator() {

        var generator = new ExampleDataGenerator<>(Book.class, LocalDateTime.now());
        generator.setData(Book::setImage, DataType.BOOK_IMAGE_URL);
        generator.setData(Book::set_bestseller, DataType.BOOLEAN_50_50);
        generator.setData(Book::setDescription, DataType.SENTENCE);
        generator.setData(Book::setId, DataType.ID);
        generator.setData(Book::setDiscount, DataType.NUMBER_UP_TO_10);
        generator.setData(Book::setPrice, DataType.NUMBER_UP_TO_10000);
        generator.setData(Book::setTitle, DataType.BOOK_TITLE);
        generator.setData(Book::setPub_date, DataType.DATE_LAST_7_DAYS);
        generator.setData(Book::setSlug, DataType.EMAIL);
        List<Book> books = generator.create(100, new Random().nextInt());
        bookRepo.saveAll(books);

    }

    public void authorGenerator() {

        var generator = new ExampleDataGenerator<>(AuthorEntity.class, LocalDateTime.now());
        generator.setData(AuthorEntity::setPhoto, DataType.BOOK_IMAGE_URL);
        generator.setData(AuthorEntity::setDescription, DataType.SENTENCE);
        generator.setData(AuthorEntity::setId, DataType.ID);
        generator.setData(AuthorEntity::setName, DataType.FULL_NAME);
        generator.setData(AuthorEntity::setSlug, DataType.EMAIL);
        List<AuthorEntity> authors = generator.create(100, new Random().nextInt());
        System.out.println("========================" + authors.size() + "=================");
        authorRepo.saveAll(authors);

    }


    public void dataForAuthor(){
        ClassLoader classLoader = getClass().getClassLoader();
        try {File file = new File(classLoader.getResource("db/changelog/data/data/author.csv").getFile());
            Reader reader = new BufferedReader(new FileReader(file));
            CsvToBean<AuthorEntity> csvReader = new CsvToBeanBuilder(reader)
                    .withType(AuthorEntity.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();
            List<AuthorEntity> results = csvReader.parse();
            authorRepo.saveAll(results);
            System.out.println(results.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void dataForBook(){
        ClassLoader classLoader = getClass().getClassLoader();
        try {File file = new File(classLoader.getResource("db/changelog/data/data/book.csv").getFile());
            Reader reader = new BufferedReader(new FileReader(file));
            CSVReader csvReader= (CSVReader) new CSVReader(reader);
            List<String[]> allRows = csvReader.readAll();
            allRows.remove(0);
            Book book;
            List<Book>books=new ArrayList<>();
            for(String[] row : allRows){
                book=new Book();
                book.setId(Integer.valueOf(row[0]));
                book.setDescription(row[1]);
                book.setDiscount(Integer.valueOf(row[2]));
                book.setImage(row[3]);
                book.set_bestseller(row[4].equals("true")?true:false);
                book.setPrice(Integer.parseInt(row[5]));
                book.setPub_date(LocalDate.parse(row[6]));
                book.setSlug(row[7]);
                book.setTitle(row[8]);
books.add(book);

            }
            bookRepo.saveAll(books);

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

}
