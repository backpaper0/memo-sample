package com.example.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import com.example.entity.Memo;

@Dao
@ConfigAutowireable
public interface MemoDao {

    @Insert
    int insert(Memo entity);

    @Update
    int update(Memo entity);

    @Select
    List<Memo> selectAll();

    @Select
    Optional<Memo> selectById(UUID id);
}
