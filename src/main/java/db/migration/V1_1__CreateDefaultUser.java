package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class V1_1__CreateDefaultUser extends BaseJavaMigration {
    @Override
    public void migrate(Context context) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO users (name, email, password) VALUES (?, ?, ?)",
                        (PreparedStatementCallback<Boolean>) preparedStatement -> {
                            preparedStatement.setString(1, "default");
                            preparedStatement.setString(2, "default@default");
                            preparedStatement.setString(3, encoder.encode("123456"));
                            return preparedStatement.execute();
                        });
    }
}
