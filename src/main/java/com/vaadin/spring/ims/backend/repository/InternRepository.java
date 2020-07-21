package com.vaadin.spring.ims.backend.repository;

import com.vaadin.spring.ims.backend.entity.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InternRepository extends JpaRepository<Intern, Long> {
    @Query("select c from Intern c " +
          "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
          "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Intern> search(@Param("searchTerm") String searchTerm);
}
