package com.dareTo.data.repositories;

import com.dareTo.data.models.ResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResetTokenRepo extends MongoRepository<ResetToken, String> {

    boolean findByToken(String token);

    ResetToken findByUserId(String userId);
}
