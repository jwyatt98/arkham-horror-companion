package com.wanderingwyatt.arkham.game.components;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class SkillTrackConverter implements AttributeConverter<SkillTrack, String> {
	private static final Logger LOGGER = LogManager.getLogger(SkillTrackConverter.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public SkillTrackConverter() {
		// no arg constructor
	}
	
	@Override
	public String convertToDatabaseColumn(SkillTrack attribute) {
		String skillTrackJson = null;
		try {
			skillTrackJson = objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error while trying to convert skill track object to json.", e);
		}
		return skillTrackJson;
	}

	@Override
	public SkillTrack convertToEntityAttribute(String dbData) {
		SkillTrack skillTrack = null;
		try {
			skillTrack = objectMapper.readValue(dbData, SkillTrack.class);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error while trying to convert json into skill track object.", e);
		}
		return skillTrack;
	}
}