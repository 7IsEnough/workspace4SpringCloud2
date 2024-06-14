package com.clearlove.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clearlove.domain.pojo.User;
import com.clearlove.domain.pojo.UserInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

  @Autowired private IUserService userService;

  @Test
  void testInsert() {
    User user = new User();
    //    user.setId(5L);
    user.setUsername("LiLei");
    user.setPassword("123");
    user.setPhone("18688990011");
    user.setBalance(200);
    user.setInfo(UserInfo.of(24, "英文老师", "female"));
    user.setCreateTime(LocalDateTime.now());
    user.setUpdateTime(LocalDateTime.now());

    userService.save(user);
  }

  @Test
  void testQuery() {
    List<User> users = userService.listByIds(Arrays.asList(1L, 2L, 4L));
    users.forEach(System.out::println);
  }

  private User buildUser(int i) {
    User user = new User();
    user.setUsername("user_" + i);
    user.setPassword("123");
    user.setPhone("" + (18688190000L + i));
    user.setBalance(2000);
    user.setInfo(UserInfo.of(24, "英文老师", "female"));
    user.setCreateTime(LocalDateTime.now());
    user.setUpdateTime(user.getCreateTime());
    return user;
  }

  @Test
  void testSaveOneByOne() {
    Long b = System.currentTimeMillis();
    for (int i = 1; i <= 100000; i++) {
      userService.save(buildUser(i));
    }
    long e = System.currentTimeMillis();
    System.out.println("耗时：" + (e - b));
  }

  @Test
  void testSaveBatch() {
    // 我们每次批量插入1000条件，插入100次即10万条数据
    // 1.准备一个容量为1000的集合
    List<User> list = new ArrayList<>(1000);
    long b = System.currentTimeMillis();
    for (int i = 1; i <= 100000; i++) {
      // 2.添加一个user
      list.add(buildUser(i));
      // 3.每1000条批量插入一次
      if (i % 1000 == 0) {
        userService.saveBatch(list);
        // 4.清空集合，准备下一批数据
        list.clear();
      }
    }
    long e = System.currentTimeMillis();
    System.out.println("耗时：" + (e - b));
  }

  @Test
  void testPage() {
    int pageNo = 1, pageSize = 2;
    Page<User> page = Page.of(pageNo, pageSize);

    page.addOrder(new OrderItem("balance", true));
    page.addOrder(new OrderItem("id", true));

    Page<User> userPage = userService.page(page);

    long total = userPage.getTotal();
    System.out.println("total = " + total);
    long pages = userPage.getPages();
    System.out.println("pages = " + pages);
    List<User> records = userPage.getRecords();
    records.forEach(System.out::println);
  }
}
