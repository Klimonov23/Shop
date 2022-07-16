package com.sh.shop.repositories;

import com.sh.shop.domain.User;
import com.sh.shop.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findFirstByName(String name);

}
