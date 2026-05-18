package com.shanzhu.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.health.entity.BodyNotes;

import java.util.List;


public interface IBodyNotesService extends IService<BodyNotes> {


    boolean insert(BodyNotes bodyNotes);

    List<BodyNotes> getBodyNotes(Integer id);

    List<BodyNotes> getBodyNotesByUsername(String username);


    void delete(Integer id);

    BodyNotes getUserBodyById(Integer notesid);

    void updateUserBody(BodyNotes bodyNotes);

    void deleteUserBodyById(Integer id);
}
