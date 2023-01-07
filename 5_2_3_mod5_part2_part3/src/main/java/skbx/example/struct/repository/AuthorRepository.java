package skbx.example.struct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skbx.example.struct.AuthorsAndBooks.AuthorEntity;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
}
