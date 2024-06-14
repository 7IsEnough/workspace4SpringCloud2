package com.clearlove.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

/**
 * @author promise
 * @date 2024/6/14 - 17:34
 */
@Data
@ApiModel(description = "分页结果")
@Accessors(chain = true)
public class PageDTO<T> {

  @ApiModelProperty("总条数")
  private Long total;

  @ApiModelProperty("总页数")
  private Long pages;

  @ApiModelProperty("集合")
  private List<T> list;

  public static <U, V> PageDTO<U> of(Page<V> page, Class<U> clazz) {
    PageDTO<U> pageDTO = new PageDTO<>();
    pageDTO.setPages(page.getPages()).setTotal(page.getTotal());
    if (!CollectionUtils.isEmpty(page.getRecords())) {
      pageDTO.setList(BeanUtil.copyToList(page.getRecords(), clazz));
    } else {
      pageDTO.setList(Collections.emptyList());
    }
    return pageDTO;
  }

  public static <U, V> PageDTO<U> of(Page<V> page, Function<V, U> function) {
    PageDTO<U> pageDTO = new PageDTO<>();
    pageDTO.setPages(page.getPages()).setTotal(page.getTotal());
    if (!CollectionUtils.isEmpty(page.getRecords())) {
      pageDTO.setList(page.getRecords().stream().map(function).collect(Collectors.toList()));
    } else {
      pageDTO.setList(Collections.emptyList());
    }
    return pageDTO;
  }
}
