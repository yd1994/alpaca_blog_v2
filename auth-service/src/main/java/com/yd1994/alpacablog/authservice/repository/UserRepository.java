package com.yd1994.alpacablog.authservice.repository;

import com.yd1994.alpacablog.authservice.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDO, Long> {

    /**
     * 通过 username 查找
     * @param username
     * @return
     */
    Optional<UserDO> findFirstByUsername(String username);

}
