package br.com.dashgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dashgo.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
