package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.Resource;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Repository
public class ResourceDALImpl implements ResourceDAL {
    private static final Logger logger = LoggerFactory.getLogger(ResourceDALImpl.class);
    private static final String COLLECTION_NAME = "resource";
    private static final String NAME = "name";
    private static final String ID = "id";
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public ResourceDALImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Resource> save(Mono<Resource> resourceMono) {
        return Mono.from(resourceMono).doOnNext(resource -> reactiveMongoTemplate.save(resource, COLLECTION_NAME).block());
    }

    @Override
    public Flux<Resource> save(Publisher<Resource> resourcePublisher) {
        List<Resource> resList = new ArrayList<>();
        Flux.from(resourcePublisher).subscribe(
                resource -> {
                    resList.add(reactiveMongoTemplate.save(resource, COLLECTION_NAME).block());
                },
                err -> logger.error("Resource subscriber received error" + err), //--> onError
                () -> logger.info("Resource subscriber got Completed event")    //--> onComplete
        );
        return Flux.fromIterable(resList);
//        return Flux.from(resourcePublisher).doOnNext(resource -> reactiveMongoTemplate.save(resource));
    }

    @Override
    public Flux<Resource> findAll() {
        return reactiveMongoTemplate.findAll(Resource.class, COLLECTION_NAME);
    }

    @Override
    public Flux<Resource> findAll(Pageable pageable) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        return reactiveMongoTemplate.find(query, Resource.class, COLLECTION_NAME);
    }

    @Override
    public Mono<Resource> findOneById(String resourceId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(resourceId));
        return reactiveMongoTemplate.findOne(query, Resource.class, COLLECTION_NAME);
    }

    @Override
    public Mono<Resource> findOneByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where(NAME).is(name));
        return reactiveMongoTemplate.findOne(query, Resource.class, COLLECTION_NAME);
    }

    @Override
    public Flux<Resource> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where(NAME).is(name));
        return reactiveMongoTemplate.find(query, Resource.class, COLLECTION_NAME);
    }
}
