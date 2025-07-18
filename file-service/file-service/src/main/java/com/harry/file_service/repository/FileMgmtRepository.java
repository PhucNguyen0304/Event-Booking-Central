package com.harry.file_service.repository;

import com.harry.file_service.entity.FileMgmt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileMgmtRepository extends MongoRepository<FileMgmt, String> {

}
