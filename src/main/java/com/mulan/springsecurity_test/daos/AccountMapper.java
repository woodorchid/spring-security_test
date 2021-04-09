package com.mulan.springsecurity_test.daos;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mulan.springsecurity_test.entities.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountMapper extends BaseMapper<Account> {

}
