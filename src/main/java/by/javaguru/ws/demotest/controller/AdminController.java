package by.javaguru.ws.demotest.controller;

import by.javaguru.ws.demotest.entity.Note;
import by.javaguru.ws.demotest.entity.User;
import by.javaguru.ws.demotest.service.NoteService;
import by.javaguru.ws.demotest.service.UserService;
import by.javaguru.ws.demotest.util.UserErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminController {

    private final NoteService noteService;
    private final UserService userService;

    @GetMapping("/all")
    public List<Note> showNotes() {
        return noteService.getNotesForUser(userService.loadUserByUsername("user@mail.ru"));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid User user,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMsg;
            List<FieldError> errors = bindingResult.getFieldErrors();
            errMsg = errors.stream()
                    .map(error -> error.getField() + " - " + error.getDefaultMessage() + ";")
                    .collect(Collectors.joining());
            throw new RuntimeException(errMsg);
        }
        noteService.createNoteForUser(user, "content");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(Exception ex) {
        LocalDateTime localDateTime = LocalDateTime.now();
        UserErrorResponse response = new UserErrorResponse(
                ex.getMessage(), localDateTime
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
