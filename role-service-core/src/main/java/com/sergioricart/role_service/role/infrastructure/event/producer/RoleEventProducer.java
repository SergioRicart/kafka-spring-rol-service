package com.sergioricart.role_service.role.infrastructure.event.producer;


import com.sergioricart.commons.infrastructure.event.producer.KafkaProducer;
import com.sergioricart.role_service.role.domain.constant.RoleConstants;
import com.sergioricart.role_service.role.domain.event.RoleCreatedDomainEvent;
import com.sergioricart.role_service.role.domain.event.RoleDeletedDomainEvent;
import com.sergioricart.role_service.role.domain.event.RoleUpdatedDomainEvent;
import com.sergioricart.role_service.role.domain.port.RoleEvent;
import com.sergioricart.role_service.role.infrastructure.event.mapper.RoleEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleEventProducer implements RoleEvent {

    private final KafkaProducer kafkaProducer;

    private final RoleEventMapper roleEventMapper;

    @Value(RoleConstants.ROLE_TOPIC)
    private String topic;


    @Override
    public void sendRoleCreatedEvent(RoleCreatedDomainEvent event) {
        log.info("Sending role created event: {}", event);
        kafkaProducer.send(topic, roleEventMapper.mapToRoleCreatedEvent(event));
    }

    @Override
    public void sendRoleUpdatedEvent(RoleUpdatedDomainEvent event) {
        log.info("Sending role updated event: {}", event);
        kafkaProducer.send(topic, roleEventMapper.mapToRoleUpdatedEvent(event));
    }

    @Override
    public void sendRoleDeletedEvent(RoleDeletedDomainEvent event) {
        log.info("Sending role deleted event: {}", event);
        kafkaProducer.send(topic, roleEventMapper.mapToRoleDeletedEvent(event));
    }
}
