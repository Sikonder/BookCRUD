package com.example.vaadincrud.repository;

import com.example.vaadincrud.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer,Long>{

    Customer findByLastNameStartsWithIgnoreCase(String filterText);
}
