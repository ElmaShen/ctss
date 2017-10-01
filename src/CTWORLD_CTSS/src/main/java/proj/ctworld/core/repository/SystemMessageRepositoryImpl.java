package proj.ctworld.core.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import proj.ctworld.core.orm.SystemMessage;

class SystemMessageRepositoryImpl {

	@Autowired
	protected EntityManager entityManager;

	public SystemMessage findBySmCode(Integer smCode) 
	{
		
		Query query = entityManager.createQuery("SELECT sm FROM SystemMessage sm WHERE sm.smCode=:smCode");
		
		query.setParameter("smCode", smCode);
		
		return (SystemMessage) query.getSingleResult();
	}
}
