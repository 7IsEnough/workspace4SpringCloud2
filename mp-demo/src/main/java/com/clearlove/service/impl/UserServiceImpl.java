package com.clearlove.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clearlove.domain.User;
import com.clearlove.mapper.UserMapper;
import com.clearlove.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author promise
 * @date 2024/6/12 - 23:57
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {}
