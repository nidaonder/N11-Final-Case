package com.nidaonder.UserService.core;

import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

public interface BaseMapper <E extends BaseEntity, SAVEREQUEST, UPDATEREQUEST, RESPONSE>{

    @Named("requestToEntity")
    E requestToEntity(SAVEREQUEST request);

    @Named("responseToEntity")
    E responseToEntity(RESPONSE response);

    @Named("entityToRequest")
    SAVEREQUEST entityToRequest(E entity);

    @Named("entityToResponse")
    RESPONSE entityToResponse(E entity);

    @Named("entityToListResponse")
    List<RESPONSE> entityToListResponse(List<E> entity);

    void update (@MappingTarget E targetEntity, UPDATEREQUEST updatingRequest);
}
