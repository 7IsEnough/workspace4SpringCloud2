package com.clearlove.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.clearlove.domain.pojo.Address;
import com.clearlove.domain.pojo.User;
import com.clearlove.domain.vo.AddressVO;
import com.clearlove.domain.vo.UserVO;
import com.clearlove.mapper.UserMapper;
import com.clearlove.service.IUserService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author promise
 * @date 2024/6/12 - 23:57
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deductBalance(Long id, Integer money) {
    User user = getById(id);

    if (Objects.isNull(user) || user.getStatus() == 2) {
      throw new RuntimeException("用户状态异常");
    }

    if (user.getBalance() < money) {
      throw new RuntimeException("用户余额不足");
    }

    int remainBalance = user.getBalance() - money;

    lambdaUpdate()
        .set(User::getBalance, remainBalance)
        .set(remainBalance == 0, User::getStatus, 2)
        .eq(User::getId, id)
        // 乐观锁
        .eq(User::getBalance, user.getBalance())
        .update();
  }

  @Override
  public List<User> queryUsers(
      String name, Integer status, Integer minBalance, Integer maxBalance) {

    return lambdaQuery()
        .like(StringUtils.isNotBlank(name), User::getUsername, name)
        .eq(Objects.nonNull(status), User::getStatus, status)
        .ge(Objects.nonNull(minBalance), User::getBalance, minBalance)
        .le(Objects.nonNull(maxBalance), User::getBalance, maxBalance)
        .list();
  }

  @Override
  public UserVO queryUserAndAddressById(Long id) {
    User user = getById(id);
    if (Objects.isNull(user) || user.getStatus() == 2) {
      throw new RuntimeException("用户状态异常");
    }
    List<Address> addresses = Db.lambdaQuery(Address.class).eq(Address::getUserId, id).list();
    UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
    if (!CollectionUtils.isEmpty(addresses)) {
      userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
    }
    return userVO;
  }
}
