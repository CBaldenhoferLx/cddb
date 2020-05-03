package com.luxoft.cddb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luxoft.cddb.beans.UserBean;

@Repository
public interface IUserRepository extends CrudRepository<UserBean, Integer> {

}
