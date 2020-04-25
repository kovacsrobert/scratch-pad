package db.migration;

import java.sql.Statement;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1__Init extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		try (Statement select = context.getConnection().createStatement()) {
			select.execute("CREATE TABLE persons (id NUMBER, name VARCHAR, age NUMBER );");
		}
	}
}
