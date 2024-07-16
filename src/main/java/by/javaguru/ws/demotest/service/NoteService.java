package by.javaguru.ws.demotest.service;

import by.javaguru.ws.demotest.entity.Note;
import by.javaguru.ws.demotest.entity.User;
import by.javaguru.ws.demotest.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public Note createNoteForUser(User user, String content) {
        Note note = new Note();
        note.setUser(user);
        note.setContent(content);
        return noteRepository.save(note);
    }

    public List<Note> getNotesForUser(User user) {
        return noteRepository.findByUser(user);
    }
}