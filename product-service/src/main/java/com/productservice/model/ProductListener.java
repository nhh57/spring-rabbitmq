package com.productservice.model;

import com.productservice.service.IProductRedisService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ProductListener {
    private static IProductRedisService productRedisService;
    private static final Logger log = LoggerFactory.getLogger(ProductListener.class);


    @PrePersist
    public void prePersist(Product product) {
        log.info("pre-persist");
    }

    @PostPersist // save =persist
    public void postPersist(Product product) {
        //update redis cache
        log.info("postPersist");
        productRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Product product) {
//       ApplicationEventPublisher.instance().publishEvent(event);
        log.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(Product product) {
        //update redis cache
        log.info("postUpdate");
        productRedisService.clear();
    }

    @PreRemove
    public void preRemove(Product product) {
        //       ApplicationEventPublisher.instance().publishEvent(event);
        log.info("preRemove");
    }

    @PostRemove
    public void postRemove(Product product){
        log.info("postRemove");
        productRedisService.clear();
    }
}
