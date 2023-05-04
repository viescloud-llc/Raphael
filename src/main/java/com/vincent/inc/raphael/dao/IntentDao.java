package com.vincent.inc.raphael.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vincent.inc.raphael.model.Intent;

public interface IntentDao extends JpaRepository<Intent, Integer>
{
	public Intent findByQuestion(String question);
	public List<Intent> findAllByQuestion(String question);

	public Intent findByContext(String context);
	public List<Intent> findAllByContext(String context);

	public Intent findByEmotion(String emotion);
	public List<Intent> findAllByEmotion(String emotion);

	@Query(value = "select * from Intent as intent where intent.question = :question and intent.context = :context and intent.emotion = :emotion", nativeQuery = true)
	public List<Intent> getAllByMatchAll(@Param("question") String question, @Param("context") String context, @Param("emotion") String emotion);

	@Query(value = "select * from Intent as intent where intent.question = :question or intent.context = :context or intent.emotion = :emotion", nativeQuery = true)
	public List<Intent> getAllByMatchAny(@Param("question") String question, @Param("context") String context, @Param("emotion") String emotion);


}