package com.clearlove.service.impl;

import com.clearlove.domain.pojo.Address;
import com.clearlove.mapper.AddressMapper;
import com.clearlove.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author promise
 * @since 2024-06-13
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
