package com.clearlove.service;

import com.clearlove.domain.pojo.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author promise
 * @date 2024/6/13 - 23:06
 */
@SpringBootTest
public class IAddressServiceTest {

  @Autowired private IAddressService addressService;

  @Test
  void testLogicDelete() {
    addressService.removeById(59L);
    Address address = addressService.getById(59L);
    System.out.println("address = " + address);
  }
}
