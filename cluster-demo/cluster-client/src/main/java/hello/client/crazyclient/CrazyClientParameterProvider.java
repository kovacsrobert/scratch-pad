package hello.client.crazyclient;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

@Service
public class CrazyClientParameterProvider implements Supplier<Object[]> {

	private final Random random = new Random();

	@Override
	public Object[] get() {
		return new Object[] { getSaltString(10) };
	}

	private String getSaltString(int length) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		while (salt.length() < length) { // length of the random string.
			int index = (int) (random.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		return salt.toString();

	}
}
