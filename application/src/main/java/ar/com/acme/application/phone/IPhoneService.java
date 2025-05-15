package ar.com.acme.application.phone;

import java.util.UUID;

import ar.com.acme.application.templates.service.IService;
import ar.com.acme.model.phone.Phone;

public interface IPhoneService extends IService<Phone, UUID> { }
