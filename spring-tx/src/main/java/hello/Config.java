package hello;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.cj.jdbc.MysqlDataSource;

@EnableTransactionManagement
@Configuration
public class Config {

	@Bean
	public DataSource dataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setPortNumber(3306);
		dataSource.setDatabaseName("tx-example");
		dataSource.setUser("root");
		dataSource.setPassword("root");
		return dataSource;
	}


}
