package com.kan.registry.health.core.service.impl;

import com.kan.registry.health.dist.exception.ServiceException;
import com.kan.registry.health.dist.to.TransactionObject;
import com.kan.registry.health.domain.AbstractDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractService<TO extends TransactionObject, D extends AbstractDomain, RE extends JpaRepository<D, Long>>{


    protected final Class<TO> toClass;
    protected final Class<D> domainClass;

    protected final RE repository;

    public AbstractService(RE repository) {
        this.repository = repository;
        Type[] genericTypes = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        toClass = (Class<TO>) genericTypes[0];
        domainClass = (Class<D>) genericTypes[1];
    }


    protected D update(D document){
        if(!repository.existsById(document.getId())){
            throw new ServiceException("Entity not exists.");
        }
        document.setUpdatedAt(LocalDateTime.now());
        return repository.save(document);
    }

    protected D create(D document){
        if(document.getId() != null){
            throw new ServiceException("Entity already exists.");
        }
        document.setCreatedAt(LocalDateTime.now());
        return repository.save(document);
    }

    public List<TO> parse(Collection<D> documents) {
        if(CollectionUtils.isEmpty(documents)){
            return List.of();
        }
        return documents.stream().map(this::parse).collect(Collectors.toList());

    }

    public List<D> inverse(Collection<TO> documents) {

        if(CollectionUtils.isEmpty(documents)){
            return List.of();
        }
        return documents.stream().map(this::parse).collect(Collectors.toList());

    }

    protected TO parse(D document){
        try{
            if(document == null){
                return null;
            }
            TO to = toClass.getConstructor().newInstance();
            to.setId(document.getId());
            to.setCreatedAt(document.getCreatedAt());
            to.setUpdatedAt(document.getUpdatedAt());
            return to;
        }catch (Exception ex){

            throw new ServiceException(ex);
        }
    }

    protected D parse(TO to){
        try{
            if(to == null){
                return null;
            }
            D d = domainClass.getConstructor().newInstance();
            d.setId(to.getId());
            d.setCreatedAt(to.getCreatedAt());
            d.setUpdatedAt(to.getUpdatedAt());
            return d;
        }catch (Exception ex){

            throw new ServiceException(ex);
        }
    }
}
