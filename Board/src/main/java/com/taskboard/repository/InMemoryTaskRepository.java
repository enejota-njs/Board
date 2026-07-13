package com.taskboard.repository;

import com.taskboard.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação simples do repositório, guardando as tarefas em memória.
 * Pode futuramente ser substituída por uma implementação com JDBC/JPA
 * sem alterar a camada de serviço, pois ambas dependem de TaskRepository.
 */
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Task salvar(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> buscarPorId(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> listarTodas() {
        return List.copyOf(tasks.values());
    }

    @Override
    public void remover(Long id) {
        tasks.remove(id);
    }

    @Override
    public boolean existe(Long id) {
        return tasks.containsKey(id);
    }

    @Override
    public Long proximoId() {
        return sequence.incrementAndGet();
    }
}