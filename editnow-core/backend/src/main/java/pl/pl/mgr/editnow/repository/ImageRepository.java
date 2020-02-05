package pl.pl.mgr.editnow.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pl.mgr.editnow.domain.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

  Image findByName(String imageName);
  boolean existsImageByNameLike(String imageName);

}
