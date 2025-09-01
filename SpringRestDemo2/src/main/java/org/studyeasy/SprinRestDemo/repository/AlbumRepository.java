package org.studyeasy.SprinRestDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.studyeasy.SprinRestDemo.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {
    
    List<Album> findByAccount_id(long id);
}
