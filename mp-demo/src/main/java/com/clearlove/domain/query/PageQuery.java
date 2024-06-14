package com.clearlove.domain.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author promise
 * @date 2024/6/14 - 17:31
 */
@Data
@ApiModel("分页查询实体")
public class PageQuery {

  @ApiModelProperty("页码")
  private Integer pageNo = 1;

  @ApiModelProperty("页码")
  private Integer pageSize = 5;

  @ApiModelProperty("排序字段")
  private String sortBy;

  @ApiModelProperty("是否升序")
  private Boolean isAsc = true;

  public <T> Page<T> toMpPage(OrderItem... items) {
    Page<T> page = Page.of(pageNo, pageSize);

    if (StringUtils.isNotBlank(sortBy)) {
      page.addOrder(new OrderItem(sortBy, isAsc));
    } else if (items != null) {
      page.addOrder(items);
    }
    return page;
  }

  public <T> Page<T> toMpPage(String defaultSortBy, Boolean isAsc) {
    return toMpPage(new OrderItem(defaultSortBy, isAsc));
  }

  public <T> Page<T> toMpPageDefaultSortByCreateTime() {
    return toMpPage(new OrderItem("create_time", false));
  }

  public <T> Page<T> toMpPageDefaultSortByUpdateTime() {
    return toMpPage(new OrderItem("update_time", false));
  }
}
