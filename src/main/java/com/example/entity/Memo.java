package com.example.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity(listener = MemoListener.class)
public class Memo {
    @Id
    public UUID id;
    public String content;
    public LocalDateTime updatedAt;
}
