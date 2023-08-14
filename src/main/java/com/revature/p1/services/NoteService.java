package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewNoteRequest;
import com.revature.p1.entities.Date;
import com.revature.p1.entities.Note;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.DateRepository;
import com.revature.p1.repositories.NoteRepository;
import com.revature.p1.utils.custom_exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepo;
    private final DateRepository dateRepo;

    public NoteService(NoteRepository noteRepo, DateRepository dateRepo) {
        this.noteRepo = noteRepo;
        this.dateRepo = dateRepo;
    }

    public Note save(NewNoteRequest req) {
        Optional<Date> dateOpt = dateRepo.findById(req.getDate_id());
        if(dateOpt.isEmpty()) {
            throw new ResourceNotFoundException("No date with id: " + req.getDate_id() + " found.");
        }
        return noteRepo.save(new Note(req.getNote(), req.getCreated_at(), req.getEdited_at(), dateOpt.get()));
    }

    public List<Note> getNotesByDate(String dateId) {
        Optional<Date> dateOpt = dateRepo.findById(dateId);
        if(dateOpt.isEmpty()) {
            throw new ResourceNotFoundException("No date with id: " + dateId + " found.");
        }
        return noteRepo.findAllByDate(dateOpt.get());
    }

}
