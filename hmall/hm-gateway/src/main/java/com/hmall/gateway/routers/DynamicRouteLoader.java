package com.hmall.gateway.routers;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author promise
 * @date 2024/6/22 - 1:16
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DynamicRouteLoader {

  private final NacosConfigManager nacosConfigManager;

  private final RouteDefinitionWriter writer;

  private final String dataId = "gateway-routes.json";
  private final String group = "DEFAULT_GROUP";

  private final Set<String> routeIds = new HashSet<>();


  @PostConstruct
  public void initRouteConfigListener() throws NacosException {
    // 项目启动时，先拉取一次配置，并添加配置监听器
    String configInfo = nacosConfigManager.getConfigService()
        .getConfigAndSignListener(dataId, group, 5000, new Listener() {
          @Override
          public Executor getExecutor() {
            return null;
          }

          @Override
          public void receiveConfigInfo(String configInfo) {
              // 监听到配置变更，需要更新路由表
            updateConfigInfo(configInfo);
          }
        });

    // 第一次读取到配置，需要更新路由表
    updateConfigInfo(configInfo);
  }


  public void updateConfigInfo(String configInfo) {
    log.debug("监听到路由配置信息：{}", configInfo);
    // 解析配置信息
    List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);
    // 删除旧路由表
    routeIds.forEach(routeId -> writer.delete(Mono.just(routeId)).subscribe());
    routeIds.clear();

    // 更新路由表
    routeDefinitions.forEach(routeDefinition -> {
      writer.save(Mono.just(routeDefinition)).subscribe();
      routeIds.add(routeDefinition.getId());
    });
  }
}
