package ar.com.acme.application.templates.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.io.Serializable;
import java.util.*;

import ar.com.acme.commons.Constants;
import ar.com.acme.model.base.entity.EntityException;
import ar.com.acme.model.base.entity.IEntity;
import ar.com.acme.model.base.repository.IRepository;

public abstract class Service<U extends IEntity<TKI>, TKI extends Serializable> implements IService<U, TKI> {
    protected final IRepository<U, TKI> repo;
    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected Service(IRepository<U, TKI> repo) {
        this.repo = repo;
    }

    public IRepository<U, TKI> getRepo() {
        return repo;
    }

    @Override
    public U persist(U instancia) throws ServiceException {
        this.validate(instancia);

        try {
            return repo.save(instancia);
        } catch (Exception ex) {
            throw new ServiceException(Constants.MSJ_REP_ERR_ATSAVEDATA, ex.toString());
        }
    }

    @Override
    public void delete(U instancia) throws ServiceException {
        instancia.kill();
        persist(instancia);
    }

    @Override
    public void undelete(U instancia) throws ServiceException {
        instancia.revive();
        persist(instancia);
    }

    @Override
    public Optional<U> findById(TKI id) {
        return repo.findById(id);
    }

    @Override
    public Optional<U> findAliveById(TKI id) {
        return findById(id).filter(i -> i.isAlive());
    }

    @Override
    public List<U> findAll() {
        return repo.findAll();
    }

    @Override
    public List<U> findAllAlive() {
        return repo.findAllAlive();
    }

    @Override
    public void validate(U entity) throws ConstraintViolationException {
        Set<ConstraintViolation<U>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<U> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage().concat(Constants.SYS_CAD_MSJ_SEPARATOR));
            }
            
            throw new EntityException(
                Constants.MSJ_REP_ERR_NOVALIDATE
                         .concat(Constants.SYS_CAD_LOGSEP)
                         .concat(sb.toString().trim()));
        }
    }
}
