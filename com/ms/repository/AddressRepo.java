package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
