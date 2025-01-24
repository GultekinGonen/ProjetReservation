package be.iccbxl.pid.repository;

import be.iccbxl.pid.model.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist,Long> {
    List<Artist>findByLastname(String lastname);
    Artist findById(long id);

}
