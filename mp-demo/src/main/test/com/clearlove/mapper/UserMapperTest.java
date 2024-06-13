package com.clearlove.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.clearlove.domain.pojo.User;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

  @Autowired private UserMapper userMapper;

  @Test
  void testInsert() {
    User user = new User();
    user.setId(5L);
    user.setUsername("Lucy");
    user.setPassword("123");
    user.setPhone("18688990011");
    user.setBalance(200);
    user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
    user.setCreateTime(LocalDateTime.now());
    user.setUpdateTime(LocalDateTime.now());
    userMapper.insert(user);
  }

  @Test
  void testSelectById() {
    User user = userMapper.selectById(5L);
    System.out.println("user = " + user);
  }

  @Test
  void testQueryByIds() {
    List<User> users =
        userMapper.selectBatchIds(Stream.of(1L, 2L, 3L, 4L).collect(Collectors.toList()));
    users.forEach(System.out::println);
  }

  @Test
  void testUpdateById() {
    User user = new User();
    user.setId(5L);
    user.setBalance(20000);
    userMapper.updateById(user);
  }

  @Test
  void testDeleteUser() {
    userMapper.deleteById(5L);
  }


  @Test
  void testQueryWrapper() {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.select("id", "username", "info", "balance").like("username", "o").ge("balance", 1000);

    List<User> users = userMapper.selectList(wrapper);
    users.forEach(System.out::println);
  }

  @Test
  void testLambdaQueryWrapper() {
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .select(User::getId, User::getUsername, User::getInfo, User::getBalance)
        .like(User::getUsername, "o")
        .ge(User::getBalance, 1000);

    List<User> users = userMapper.selectList(wrapper);
    users.forEach(System.out::println);
  }


  @Test
  void testUpdateByQueryWrapper() {
    User user = new User();
    user.setBalance(2000);

    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", "jack");

    userMapper.update(user, queryWrapper);
  }


  @Test
  void testUpdateByUpdateWrapper() {
    UpdateWrapper<User> wrapper = new UpdateWrapper<User>().setSql("balance = balance - 200")
        .in("id", 1, 2, 4);

    userMapper.update(null, wrapper);
  }


  @Test
  void testUpdateByCustomSql() {
    int amount = 200;
    LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>().in(User::getId, 1,2,4);

    userMapper.updateBalanceByIds(lambdaQueryWrapper, amount);
  }
}
