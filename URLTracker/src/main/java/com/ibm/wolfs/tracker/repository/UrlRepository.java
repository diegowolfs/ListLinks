package com.ibm.wolfs.tracker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ibm.wolfs.tracker.model.Url;

/**
 * 
 * This Interface is used to map CrudRepository. In that case we are extending
 * this interface from CrudRepository to user persistence with H2 DB.
 *
 * @author Wolfshorndl, D. A.
 * 
 */
public interface UrlRepository extends CrudRepository<Url, Long> {

	List<Url> findByLink(String link);

}
