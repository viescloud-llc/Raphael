package com.vincent.inc.raphael.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vincent.inc.raphael.model.Intent;

public interface IntentDao extends JpaRepository<Intent, Long>
{
	public Intent findByQuestion(String question);
	public List<Intent> findAllByQuestion(String question);

	public Intent findByContext(String context);
	public List<Intent> findAllByContext(String context);

	public Intent findByEmotion(String emotion);
	public List<Intent> findAllByEmotion(String emotion);
}