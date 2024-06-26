package com.clearlove.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.clearlove.enums.UserStatus;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "user", autoResultMap = true)
public class User {

  /** 用户id */
  //    @TableId(type = IdType.ASSIGN_ID)
  private Long id;

  /** 用户名 */
  @TableField("`username`")
  private String username;

  /** 密码 */
  private String password;

  /** 注册手机号 */
  private String phone;

  /** 详细信息 */
  @TableField(typeHandler = JacksonTypeHandler.class)
  private UserInfo info;

  /** 使用状态（1正常 2冻结） */
  private UserStatus status;

  /** 账户余额 */
  private Integer balance;

  /** 创建时间 */
  private LocalDateTime createTime;

  /** 更新时间 */
  private LocalDateTime updateTime;
}
