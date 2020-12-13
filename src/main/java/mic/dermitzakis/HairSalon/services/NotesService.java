/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.services;

import java.util.Optional;
import mic.dermitzakis.HairSalon.model.Note;
import mic.dermitzakis.HairSalon.repository.NotesRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author mderm
 */
@Service
public class NotesService {
    private final NotesRepository notesRepository;

    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }
    
    public Optional<Note> save(Note note){
        return Optional.of(notesRepository.save(note));
    }
}
