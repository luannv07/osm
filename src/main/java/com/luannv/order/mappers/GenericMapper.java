package com.luannv.order.mappers;

public interface GenericMapper<Entity, RequestDTO, ResponseDTO> {
	Entity toEntity(RequestDTO requestDTO);
	ResponseDTO toResponseDTO(Entity entity);
}
