package ba.sum.fsre.knjiznica.repositories;

import ba.sum.fsre.knjiznica.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
