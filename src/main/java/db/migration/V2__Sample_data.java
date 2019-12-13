package db.migration;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.example.dao.MemoDao;
import com.example.entity.Memo;

public class V2__Sample_data extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {

        DataSource dataSource = new SingleConnectionDataSource(context.getConnection(), true);
        Dialect dialect = new H2Dialect();
        Config config = new Config() {
            @Override
            public DataSource getDataSource() {
                return dataSource;
            }

            @Override
            public Dialect getDialect() {
                return dialect;
            }
        };

        MemoDao dao = (MemoDao) Class.forName("com.example.dao.MemoDaoImpl")
                .getConstructor(Config.class).newInstance(config);

        Function<String, Memo> builder = content -> {
            Memo entity = new Memo();
            entity.id = UUID.randomUUID();
            entity.content = content;
            entity.updatedAt = LocalDateTime.now();
            return entity;
        };

        Stream.of(
                "# Hello\n\nHello, world!\n\n* foo\n* bar\n* baz\n",
                "# How to run\n\n```\ngradlew build\njava -jar build/libs/memo.jar\n```\n",
                "# Icon\n\n![](https://www.gravatar.com/avatar/e107c65b007e7abb6b2e53054428fb5a)")
                .map(builder)
                .forEach(dao::insert);
    }
}
