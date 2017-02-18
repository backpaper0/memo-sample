package db.migration;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.seasar.doma.boot.autoconfigure.DomaConfig;
import org.seasar.doma.boot.autoconfigure.DomaConfigBuilder;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.ConfigSupport;
import org.seasar.doma.jdbc.Naming;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import com.example.dao.MemoDao;
import com.example.dao.MemoDaoImpl;
import com.example.entity.Memo;

public class V2__Sample_data implements JdbcMigration {

    @Override
    public void migrate(Connection connection) throws Exception {

        Config config = new DomaConfig(new DomaConfigBuilder()
                .dialect(new H2Dialect())
                .dataSource(new SingleConnectionDataSource(connection, true))
                .naming(Naming.SNAKE_LOWER_CASE)
                .sqlFileRepository(ConfigSupport.defaultSqlFileRepository)
                .entityListenerProvider(ConfigSupport.defaultEntityListenerProvider));

        MemoDao dao = new MemoDaoImpl(config);

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
