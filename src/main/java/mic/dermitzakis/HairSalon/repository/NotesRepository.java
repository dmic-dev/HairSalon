/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.repository;

import mic.dermitzakis.HairSalon.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mderm
 */
@Repository
public interface NotesRepository extends JpaRepository<Note, Long>{
    
}
