package com.example.entity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.stereotype.Component;

@Component
public class MemoListener implements EntityListener<Memo> {

    private final Clock clock;

    public MemoListener() {
        this.clock = Clock.systemDefaultZone();
    }

    public MemoListener(Clock clock) {
        this.clock = Objects.requireNonNull(clock);
    }

    @Override
    public void preInsert(Memo entity, PreInsertContext<Memo> context) {
        entity.updatedAt = LocalDateTime.now(clock);
    }

    @Override
    public void preUpdate(Memo entity, PreUpdateContext<Memo> context) {
        entity.updatedAt = LocalDateTime.now(clock);
    }
}
