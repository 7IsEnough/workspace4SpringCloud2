package com.clearlove.domain.vo;

import com.clearlove.domain.pojo.UserInfo;
import com.clearlove.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
@ApiModel(description = "用户VO实体")
public class UserVO {

  @ApiModelProperty("用户id")
  private Long id;

  @ApiModelProperty("用户名")
  private String username;

  @ApiModelProperty("详细信息")
  private UserInfo info;

  @ApiModelProperty("使用状态（1正常 2冻结）")
  private UserStatus status;

  @ApiModelProperty("账户余额")
  private Integer balance;

  @ApiModelProperty("用户收货地址")
  private List<AddressVO> addresses;
}
