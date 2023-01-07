package skbx.example.struct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skbx.example.struct.AuthorsAndBooks.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
