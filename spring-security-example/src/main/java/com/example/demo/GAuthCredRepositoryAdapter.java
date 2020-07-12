package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrenstrange.googleauth.ICredentialRepository;

@Service
public class GAuthCredRepositoryAdapter implements ICredentialRepository {

	private final GAuthCredRepository gAuthCredRepository;

	@Autowired
	public GAuthCredRepositoryAdapter(GAuthCredRepository gAuthCredRepository) {
		this.gAuthCredRepository = gAuthCredRepository;
	}

	@Override
	public String getSecretKey(String userName) {
		Optional<GAuthCred> gAuthCred = gAuthCredRepository.findByUserName(userName);
		return gAuthCred.map(GAuthCred::getSecretKey)
				.orElseThrow(() -> new IllegalArgumentException("No record for userName: " + userName));
	}

	@Override
	public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
		GAuthCred gAuthCred = new GAuthCred();
		gAuthCred.setUserName(userName);
		gAuthCred.setSecretKey(secretKey);
		gAuthCredRepository.save(gAuthCred);
	}
}
