package btc_shop.database.interfaces;

import btc_shop.database.entities.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface IApplicationUserRepository extends CrudRepository<ApplicationUser, Integer> {

}
