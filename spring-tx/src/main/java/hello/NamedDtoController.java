package hello;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NamedDtoController {

	@Autowired
	private NamedDtoRepository userRepository;
	
	@GetMapping(path="/new")
	public @ResponseBody NamedDto newNamedDto(@RequestParam String name) {
		NamedDto namedDto = new NamedDto();
		namedDto.setId(UUID.randomUUID().toString());
		namedDto.setName(name);
		userRepository.save(namedDto);
		return namedDto;
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<NamedDto> getAllUsers() {
		return userRepository.findAll();
	}
}
