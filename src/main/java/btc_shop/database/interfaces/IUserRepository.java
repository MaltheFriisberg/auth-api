package btc_shop.database.interfaces;

import btc_shop.models.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, Integer> {

}
