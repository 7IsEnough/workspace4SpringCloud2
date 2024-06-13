package com.clearlove.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clearlove.domain.pojo.User;
import java.util.List;

/**
 * @author promise
 * @date 2024/6/12 - 23:56
 */
public interface IUserService extends IService<User> {

  void deductBalance(Long id, Integer money);

  List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance);
}
