package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CommunityDAOImpl implements CommunityDAO{

	@PersistenceContext
	private EntityManager em;
	
	/*
	 * CRUD Operations
	 */	
	
	@Override
	public Community insert(Community com) {
		em.persist(com);
		return com;
	}

	@Override
	public Community update(Community com) {
		return em.merge(com);
	}

	@Override
	public void delete(Community com) {
		em.remove(com);
	}
	
	/**
	 * findById Method to find an specific community by the id
	 */
	@Override
	public Community findById(int id) {
		return em.find(Community.class, id);
	}
	
	/**
	 * findByName Method to find an specific community by the name. 
	 * Hibernate CriteriaBuilder is used to get a single result.
	 */
	@Override
	public Community findByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		criteria.where(builder.equal(community.get("name"), name));
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	

	/**
	 * findPendigCommunities Method to find all communities which art approved. This Method could only be invoked by (Portal-)admin
	 */
	@Override
	public List<Community> findPendingCommunities() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		criteria.where(builder.equal(community.get("state"), new EnumerationItem(1)));
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * findApprovedCommunities find all approved and published communities.
	 */
	@Override
	public List<Community> findApprovedCommunities() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		criteria.where(builder.equal(community.get("state"), new EnumerationItem(2)));
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 *findAll communities whithout any specific criteria 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Community> findAll() {
		final String hql = "SELECT c FROM " + Community.class.getName() + " AS c";	    
	    return em.createQuery(hql).getResultList();
	}

	@Override
	public Community createCommunity(String name, String description) {
		Community c = new Community();
		c.setName(name);
		c.setDescription(description);
		c.setState(new EnumerationItem(1));
		insert(c);
		return c;
	}
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}



}
