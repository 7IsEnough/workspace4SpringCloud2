package com.clearlove.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.clearlove.domain.pojo.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {

  void saveUser(User user);

  void deleteUser(Long id);

  void updateUser(User user);

  User queryUserById(@Param("id") Long id);

  List<User> queryUserByIds(@Param("ids") List<Long> ids);

  void updateBalanceByIds(
      @Param(Constants.WRAPPER) LambdaQueryWrapper<User> lambdaQueryWrapper,
      @Param("amount") int amount);

  @Update("update user set balance = balance - #{money} where id = #{id}")
  void deductBalance(@Param("id") Long id, @Param("money") Integer money);
}
