package db.migration;

import java.sql.Statement;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V3__PersonOtherName extends BaseJavaMigration  {

	@Override
	public void migrate(Context context) throws Exception {
		try (Statement select = context.getConnection().createStatement()) {
			select.execute("ALTER TABLE persons ADD other VARCHAR;");

			select.execute("UPDATE persons SET other = 'Huff' WHERE id = 1;");
			select.execute("UPDATE persons SET other = 'Woolley' WHERE id = 2;");
			select.execute("UPDATE persons SET other = 'Robin' WHERE id = 3;");
		}
	}
}
