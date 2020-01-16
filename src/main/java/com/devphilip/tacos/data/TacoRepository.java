package com.devphilip.tacos.data;

import com.devphilip.tacos.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}

/*public interface TacoRepository {

    Taco save(Taco taco);

}*/
