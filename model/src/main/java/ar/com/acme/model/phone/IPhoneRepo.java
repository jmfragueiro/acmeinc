package ar.com.acme.model.phone;

import org.springframework.stereotype.Repository;

import ar.com.acme.model.base.repository.IRepository;

import java.util.UUID;

@Repository
public interface IPhoneRepo extends IRepository<Phone, UUID> { }
