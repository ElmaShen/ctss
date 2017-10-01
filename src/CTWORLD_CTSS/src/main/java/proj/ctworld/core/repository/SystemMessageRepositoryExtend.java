package proj.ctworld.core.repository;

import org.springframework.beans.factory.annotation.Autowired;

import proj.ctworld.core.orm.SystemMessage;

interface SystemMessageRepositoryExtend {

	public SystemMessage findBySmCode(Integer smCode);
}
