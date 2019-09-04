package hello;

import org.springframework.data.repository.CrudRepository;

public interface NamedDtoRepository extends CrudRepository<NamedDto, Integer> {
}
