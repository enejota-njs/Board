package com.taskboard.exception;

/**
 * Lançada quando uma tarefa solicitada não é encontrada no repositório.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Tarefa com id %d não foi encontrada".formatted(id));
    }
}