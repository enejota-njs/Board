package com.taskboard.service;

import com.taskboard.model.Task;
import com.taskboard.model.TaskStatus;

import java.util.List;

/**
 * Contrato da camada de regras de negócio do board de tarefas.
 * A camada de service isola a lógica de aplicação da camada de
 * apresentação (controller) e da camada de dados (repository).
 */
public interface TaskService {

    Task criarTarefa(String titulo, String descricao);

    Task buscarTarefa(Long id);

    List<Task> listarTodas();

    List<Task> listarPorStatus(TaskStatus status);

    Task avancarStatus(Long id);

    Task moverPara(Long id, TaskStatus status);

    Task editarTarefa(Long id, String novoTitulo, String novaDescricao);

    void removerTarefa(Long id);
}