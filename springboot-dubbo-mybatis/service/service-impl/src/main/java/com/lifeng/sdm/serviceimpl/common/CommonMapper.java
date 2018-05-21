package com.lifeng.sdm.serviceimpl.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 *通用Mapper
 * add by lifeng
 */
public interface CommonMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
