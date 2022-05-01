package at.ac.fhcampuswien.se.group1.locationservice.service;

import at.ac.fhcampuswien.se.group1.locationservice.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {
    
    private MongoOperations mongoOperations;
    
    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
    
    //TODO Always starts with three - perhaps better create index via database
    public Integer generateSequence(String seqName) {
        
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        
        if (!Objects.isNull(counter) && counter.getSeq() <= 2) {
            return 3;
        }
        return counter.getSeq();
    }
}
