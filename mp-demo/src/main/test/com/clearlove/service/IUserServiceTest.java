package com.clearlove.service;

import com.clearlove.domain.User;
import com.clearlove.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author promise
 * @date 2024/6/13 - 0:01
 */
@SpringBootTest
public class IUserServiceTest {

  @Autowired
  private IUserService userService;



  @Test
  void testInsert() {
    User user = new User();
//    user.setId(5L);
    user.setUsername("LiLei");
    user.setPassword("123");
    user.setPhone("18688990011");
    user.setBalance(200);
    user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
    user.setCreateTime(LocalDateTime.now());
    user.setUpdateTime(LocalDateTime.now());


    userService.save(user);
  }

  @Test
  void testQuery() {
    List<User> users = userService.listByIds(Arrays.asList(1L, 2L, 4L));
    users.forEach(System.out::println);
  }

}
