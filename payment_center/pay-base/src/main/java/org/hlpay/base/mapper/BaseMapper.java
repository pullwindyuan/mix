package org.hlpay.base.mapper;

import java.util.List;

public interface BaseMapper<M, I> {
  int baseInsert(M paramM) throws Exception;
  
  int baseDelete(I paramI) throws Exception;
  
  int baseUpdate(M paramM) throws Exception;
  
  int baseUpdateOrderDetail(M paramM) throws Exception;
  
  List<M> baseLimitSelect(M paramM) throws Exception;
  
  M baseInfo(M paramM) throws Exception;
  
  Integer count() throws Exception;
}

