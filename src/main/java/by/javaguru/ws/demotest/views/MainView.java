package by.javaguru.ws.demotest.views;

import by.javaguru.ws.demotest.entity.Note;
import by.javaguru.ws.demotest.entity.User;
import by.javaguru.ws.demotest.service.NoteService;
import by.javaguru.ws.demotest.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Route("main")
public class MainView extends VerticalLayout {
    private final NoteService noteService;

    private User currentUser;

    public MainView(NoteService noteService, UserService userService) {
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            currentUser = userService.loadUserByUsername(username);
            if (currentUser != null && password.equals(currentUser.getPassword())) {
                showNotes();
            } else {
                Notification.show("Invalid credentials");
            }
        });

        add(usernameField, passwordField, loginButton);
        this.noteService = noteService;
    }

    private void showNotes() {
        removeAll();
        List<Note> notes = noteService.getNotesForUser(currentUser);
        notes.forEach(note -> add(new Label(note.getContent())));

        TextField noteField = new TextField("New Note");
        Button addNoteButton = new Button("Add Note", event -> {
            String content = noteField.getValue();
            noteService.createNoteForUser(currentUser, content);
            showNotes();
        });

        add(noteField, addNoteButton);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
