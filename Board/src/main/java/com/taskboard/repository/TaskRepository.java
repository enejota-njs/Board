package com.taskboard.repository;

import com.taskboard.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Contrato da camada de persistência de tarefas.
 * Definir uma interface permite trocar a implementação (memória, arquivo,
 * banco de dados) sem impactar as camadas superiores (Dependency Inversion).
 */
public interface TaskRepository {

    Task salvar(Task task);

    Optional<Task> buscarPorId(Long id);

    List<Task> listarTodas();

    void remover(Long id);

    boolean existe(Long id);

    Long proximoId();
}