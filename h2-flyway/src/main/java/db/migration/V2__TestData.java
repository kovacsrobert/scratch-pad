package db.migration;

import java.sql.Statement;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V2__TestData extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		try (Statement select = context.getConnection().createStatement()) {
			select.execute("INSERT INTO persons (id, name, age) VALUES (1, 'Tom', 20);");
			select.execute("INSERT INTO persons (id, name, age) VALUES (2, 'Jake', 21);");
			select.execute("INSERT INTO persons (id, name, age) VALUES (3, 'Mark', 22);");
		}
	}
}
