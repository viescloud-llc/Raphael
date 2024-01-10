package com.vincent.inc.raphael.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vincent.inc.raphael.model.TTS;

public interface TTSDao extends JpaRepository<TTS, Integer> {
    public TTS findByText(String text);
	public List<TTS> findAllByText(String text);
}
