package com.ibm.wolfs.linklist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ibm.wolfs.linklist.model.Link;

/**
 * 
 * This Interface is used to map CrudRepository. In that case we are extending
 * this interface from CrudRepository to user persistence with H2 DB.
 *
 * @author Wolfshorndl, D. A.
 * 
 */
public interface LinkRepository extends CrudRepository<Link, Long> {

	List<Link> findByLink(String link);

}
