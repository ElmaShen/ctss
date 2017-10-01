package proj.ctworld.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import proj.ctworld.core.orm.*;

@Repository
public interface ImageStorageRepository extends JpaRepository<ImageStorage, Long>  {

	@Query("select s from ImageStorage s where s.isRelatedType = ?1 and s.isRelatedId=?2")
	public List<ImageStorage> getImage(String relatedType,long relatedId) ;
	
	@Query("select s from ImageStorage s where s.isRelatedType like ?1 and s.isRelatedId=?2")
	public List<ImageStorage> getLikeTypeImage(String relatedType,long relatedId) ;
}
