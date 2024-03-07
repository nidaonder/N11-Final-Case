package com.nidaonder.UserService.core;

import java.util.List;

public interface BaseService <E extends BaseEntity, SAVEREQUEST, UPDATEREQUEST, RESPONSE>{

    List<RESPONSE> findAll();
    RESPONSE findById(Long id);
    RESPONSE save(SAVEREQUEST request);
    RESPONSE update(Long id, UPDATEREQUEST request);
    void deleteById(Long id);
}
