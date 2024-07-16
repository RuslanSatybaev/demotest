package by.javaguru.ws.demotest.repository;

import by.javaguru.ws.demotest.entity.Note;
import by.javaguru.ws.demotest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user);
}