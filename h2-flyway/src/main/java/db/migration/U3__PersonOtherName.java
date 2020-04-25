package db.migration;

import java.sql.Statement;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class U3__PersonOtherName extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		try (Statement select = context.getConnection().createStatement()) {
			select.execute("ALTER TABLE persons DROP COLUMN other;");
		}
	}
}
