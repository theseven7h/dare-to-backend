package com.dareTo.data.repositories;

import com.dareTo.data.models.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepo extends MongoRepository<Plan, String> {

//    @Query("{'completed': true}")
//    List<Plan> findByCompletedTrue(String userId);
}
