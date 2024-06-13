package com.clearlove.controller;

import cn.hutool.core.bean.BeanUtil;
import com.clearlove.domain.dto.UserFormDTO;
import com.clearlove.domain.pojo.User;
import com.clearlove.domain.query.UserQuery;
import com.clearlove.domain.vo.UserVO;
import com.clearlove.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author promise
 * @date 2024/6/13 - 16:05
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;

  @ApiOperation("新增用户接口")
  @PostMapping
  public void saveUser(@RequestBody UserFormDTO userFormDTO) {
    userService.save(BeanUtil.copyProperties(userFormDTO, User.class));
  }

  @ApiOperation("删除用户接口")
  @DeleteMapping("/{id}")
  public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
    userService.removeById(id);
  }


  @ApiOperation("根据id查询用户接口")
  @GetMapping("/{id}")
  public UserVO queryUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
    return userService.queryUserAndAddressById(id);
  }

  @ApiOperation("根据id批量查询用户接口")
  @GetMapping()
  public List<UserVO> queryUserByIds(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids) {
    return userService.queryUserAndAddressByIds(ids);
  }

  @ApiOperation("扣减用户余额接口")
  @PutMapping("/{id}/deduction/{money}")
  public void deductBalance(
      @ApiParam("用户id") @PathVariable("id") Long id,
      @ApiParam("扣减的金额") @PathVariable("money") Integer money
      ) {
    userService.deductBalance(id, money);
  }

  @ApiOperation("根据复杂条件查询用户接口")
  @GetMapping("/list")
  public List<UserVO> queryUsers(@ApiParam("用户id集合")UserQuery userQuery) {
    List<User> users = userService.queryUsers(userQuery.getName(), userQuery.getStatus(), userQuery.getMinBalance(), userQuery.getMaxBalance());
    return BeanUtil.copyToList(users, UserVO.class);
  }
}
