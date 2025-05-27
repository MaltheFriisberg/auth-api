package btc_shop.database.interfaces;

import btc_shop.database.entities.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface IAppUserRepository extends CrudRepository<AppUser, Integer> {

}
