package org.hlpay.base.mapper;

import org.hlpay.base.model.RestDate;
import org.hlpay.base.model.RestDateExample;
import org.springframework.stereotype.Repository;

@Repository
public interface RestDateDao extends MyBatisBaseDao<RestDate, Long, RestDateExample> {}
