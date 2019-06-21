package com.example.customdependencyresolver.domain2;

import org.springframework.stereotype.Service;

@Service
public class VeryOtherService {

	private final VeryOtherComponent<Integer> integerVeryOtherComponent;
	private final VeryOtherComponent<String> stringVeryOtherComponent;
	private final VeryOtherComponent<String> string2VeryOtherComponent;

	public VeryOtherService(
			VeryOtherComponent<Integer> integerVeryOtherComponent,
			VeryOtherComponent<String> stringVeryOtherComponent,
			VeryOtherComponent<String> string2VeryOtherComponent) {
		this.integerVeryOtherComponent = integerVeryOtherComponent;
		this.stringVeryOtherComponent = stringVeryOtherComponent;
		this.string2VeryOtherComponent = string2VeryOtherComponent;
	}
}
