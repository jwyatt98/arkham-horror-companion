package com.wanderingwyatt.arkham.dao.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanderingwyatt.arkham.game.components.SkillTrack;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SkillTrackConverter implements AttributeConverter<SkillTrack, String> {
	private static final String ERROR_WHILE_TRYING_TO_CONVERT_SKILL_TRACK_OBJECT_TO_JSON = "Error while trying to convert skill track object to json.";
	private static final String ERROR_WHILE_TRYING_TO_CONVERT_JSON_INTO_SKILL_TRACK_OBJECT = "Error while trying to convert json into skill track object.";
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public String convertToDatabaseColumn(SkillTrack attribute) {
		String skillTrackJson = null;
		try {
			skillTrackJson = objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new ConversionException(ERROR_WHILE_TRYING_TO_CONVERT_SKILL_TRACK_OBJECT_TO_JSON, e);
		}
		return skillTrackJson;
	}

	@Override
	public SkillTrack convertToEntityAttribute(String dbData) {
		SkillTrack skillTrack = null;
		try {
			skillTrack = objectMapper.readValue(dbData, SkillTrack.class);
		} catch (JsonProcessingException e) {
			throw new ConversionException(ERROR_WHILE_TRYING_TO_CONVERT_JSON_INTO_SKILL_TRACK_OBJECT, e);
		}
		return skillTrack;
	}
}
