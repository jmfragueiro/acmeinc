package ar.com.acme.application.templates.controller;

import org.springframework.web.bind.annotation.*;

import ar.com.acme.model.base.entity.IEntity;
import ar.com.acme.model.base.repository.ItemNotFoundException;
import ar.com.acme.application.templates.service.IService;
import ar.com.acme.commons.Constants;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class Controller<U extends IEntity<TKI>, TKI extends Serializable, WI, WO> implements IController<U, TKI, WI, WO> {
    private final IService<U, TKI> service;

    protected Controller(IService<U, TKI> service) {
        this.service = service;
    }

    @Override
    public IService<U, TKI> getService() {
        return service;
    }

    @GetMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public WO view(@PathVariable("userId") TKI userId) {
        return toWebOutModel(getService().findAliveById(userId).orElseThrow(() -> new ItemNotFoundException(userId.toString())));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<WO> list() {
        return getService().findAllAlive().stream().map(this::toWebOutModel).toList();
    }

    @PostMapping(consumes = Constants.SYS_CAD_APP_MIMETYPE_JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public WO add(@Valid @RequestBody WI object) throws IOException {
        return toWebOutModel(getService().persist(fromWebInModel(object)));
    }

    @PutMapping(consumes = Constants.SYS_CAD_APP_MIMETYPE_JSON)
    @ResponseStatus(HttpStatus.OK)
    public WO update(@Valid @RequestBody WI object) throws IOException {
        return toWebOutModel(getService().persist(fromWebInModel(object)));
    }

    @DeleteMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("userId") TKI userId) throws IOException {
        getService().findAliveById(userId).ifPresent(service::delete);
    }
}
