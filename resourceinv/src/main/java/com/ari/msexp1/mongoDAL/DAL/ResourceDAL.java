package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.Resource;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface ResourceDAL {
    Mono<Resource> save(Mono<Resource> resource);
    Flux<Resource> save(Publisher<Resource> resources);
    Flux<Resource> findAll();

    Flux<Resource> findAll(Pageable pageable);

    Mono<Resource> findOneById(String resourceId);
    Mono<Resource> findOneByName(String name);
    Flux<Resource> findByName(String name);

}