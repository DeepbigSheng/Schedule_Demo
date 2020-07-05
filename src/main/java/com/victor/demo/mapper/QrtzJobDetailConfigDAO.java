package com.victor.demo.mapper;

import com.victor.demo.domain.QrtzJobDetailConfig;
import com.victor.demo.domain.QrtzJobDetailConfigExample;
import org.springframework.stereotype.Repository;

/**
 * QrtzJobDetailConfigDAO继承基类
 */
@Repository
public interface QrtzJobDetailConfigDAO extends MyBatisBaseDao<QrtzJobDetailConfig, Integer, QrtzJobDetailConfigExample> {
}