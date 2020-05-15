package com.luxoft.cddb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luxoft.cddb.beans.user.UserRoleBean;

@Repository
public interface IUserRoleRepository extends CrudRepository<UserRoleBean, Integer> {

}
