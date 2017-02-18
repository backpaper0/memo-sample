package com.example.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.dao.MemoDao;
import com.example.entity.Memo;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private final MemoDao dao;

    public MemoController(MemoDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @GetMapping
    List<Memo> getAll() {
        return dao.selectAll();
    }

    @PostMapping
    Memo create(@RequestParam String content) {
        Memo entity = new Memo();
        entity.id = UUID.randomUUID();
        entity.content = content;
        dao.insert(entity);
        return entity;
    }

    @PostMapping("{id}")
    void update(@PathVariable UUID id, @RequestParam String content) {
        dao.selectById(id).ifPresent(entity -> {
            entity.content = content;
            dao.update(entity);
        });
    }
}
