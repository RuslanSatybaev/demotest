package by.javaguru.ws.demotest.util;

import by.javaguru.ws.demotest.entity.Role;
import by.javaguru.ws.demotest.entity.User;
import by.javaguru.ws.demotest.service.NoteService;
import by.javaguru.ws.demotest.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Init {

    private final UserService userService;
    private final NoteService noteService;

    @PostConstruct
    public void initializeDataBase() {

        var user = new User(1L, "user@mail.ru", "user", Role.USER);
        userService.saveUser(user);
        var admin = new User(2L, "admin@mail.ru", "user", Role.ADMIN);
        userService.saveUser(admin);
        var manager = new User(3L, "manager@mail.ru", "user", Role.MANAGER);
        userService.saveUser(manager);

        noteService.createNoteForUser(user,"ayticom");
        noteService.createNoteForUser(user,"userPage");
        noteService.createNoteForUser(admin,"adminPage");
        noteService.createNoteForUser(manager,"product");

    }
}
