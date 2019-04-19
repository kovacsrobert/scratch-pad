package hello.web.provider;

import static hello.web.config.Profiles.FAKE_DAO;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile(FAKE_DAO)
public class FakeHelloProvider implements HelloProvider {

	@Override
	public String welcome(String name) {
		return "Bonjour, " + name;
	}
}
