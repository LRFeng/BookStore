package com.aring.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.aring.bean.Store;
import com.aring.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Store getStoreByPrimary(int id) throws Exception {
		return em.find(Store.class, id);
	}

}
