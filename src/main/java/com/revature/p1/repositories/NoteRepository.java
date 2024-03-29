package com.revature.p1.repositories;

import com.revature.p1.entities.Date;
import com.revature.p1.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

    List<Note> findAllByDate(Date date);

}
