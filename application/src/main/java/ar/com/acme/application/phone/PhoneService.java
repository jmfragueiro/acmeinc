package ar.com.acme.application.phone;

import org.springframework.stereotype.Service;

import ar.com.acme.model.phone.IPhoneRepo;
import ar.com.acme.model.phone.Phone;

import java.util.UUID;

@Service
public class PhoneService extends ar.com.acme.application.templates.service.Service<Phone, UUID> implements IPhoneService {
    public PhoneService(IPhoneRepo phoneRepo) {
        super(phoneRepo);
    }
}
