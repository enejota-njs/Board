package com.taskboard.service;

import com.taskboard.exception.TaskNotFoundException;
import com.taskboard.model.Task;
import com.taskboard.model.TaskStatus;
import com.taskboard.repository.TaskRepository;

import java.util.List;
import java.util.Objects;

/**
 * Implementação padrão de TaskService.
 * Depende apenas da interface TaskRepository (não de uma implementação
 * concreta), seguindo o princípio de Inversão de Dependência (SOLID).
 */
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository não pode ser nulo");
    }

    @Override
    public Task criarTarefa(String titulo, String descricao) {
        Long id = repository.proximoId();
        Task task = new Task(id, titulo, descricao);
        return repository.salvar(task);
    }

    @Override
    public Task buscarTarefa(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public List<Task> listarTodas() {
        return repository.listarTodas();
    }

    @Override
    public List<Task> listarPorStatus(TaskStatus status) {
        return repository.listarTodas().stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    @Override
    public Task avancarStatus(Long id) {
        Task task = buscarTarefa(id);
        task.avancarStatus();
        return repository.salvar(task);
    }

    @Override
    public Task moverPara(Long id, TaskStatus status) {
        Task task = buscarTarefa(id);
        task.alterarStatus(status);
        return repository.salvar(task);
    }

    @Override
    public Task editarTarefa(Long id, String novoTitulo, String novaDescricao) {
        Task task = buscarTarefa(id);
        task.atualizarTitulo(novoTitulo);
        task.atualizarDescricao(novaDescricao);
        return repository.salvar(task);
    }

    @Override
    public void removerTarefa(Long id) {
        if (!repository.existe(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.remover(id);
    }
}