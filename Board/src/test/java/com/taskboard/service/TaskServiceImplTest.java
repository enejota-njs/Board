package com.taskboard.service;

import com.taskboard.exception.TaskNotFoundException;
import com.taskboard.model.Task;
import com.taskboard.model.TaskStatus;
import com.taskboard.repository.InMemoryTaskRepository;
import com.taskboard.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceImplTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        TaskRepository repository = new InMemoryTaskRepository();
        service = new TaskServiceImpl(repository);
    }

    @Test
    void deveCriarECBuscarTarefa() {
        Task criada = service.criarTarefa("Estudar Spring", "Focar em REST");
        Task encontrada = service.buscarTarefa(criada.getId());

        assertEquals(criada.getId(), encontrada.getId());
        assertEquals("Estudar Spring", encontrada.getTitulo());
    }

    @Test
    void deveLancarExcecaoAoBuscarTarefaInexistente() {
        assertThrows(TaskNotFoundException.class, () -> service.buscarTarefa(999L));
    }

    @Test
    void deveListarTodasAsTarefas() {
        service.criarTarefa("Tarefa 1", "desc 1");
        service.criarTarefa("Tarefa 2", "desc 2");

        List<Task> tarefas = service.listarTodas();

        assertEquals(2, tarefas.size());
    }

    @Test
    void deveFiltrarTarefasPorStatus() {
        Task t1 = service.criarTarefa("Tarefa 1", "desc 1");
        service.criarTarefa("Tarefa 2", "desc 2");
        service.avancarStatus(t1.getId());

        List<Task> emAndamento = service.listarPorStatus(TaskStatus.IN_PROGRESS);
        List<Task> aFazer = service.listarPorStatus(TaskStatus.TODO);

        assertEquals(1, emAndamento.size());
        assertEquals(1, aFazer.size());
    }

    @Test
    void deveEditarTarefa() {
        Task task = service.criarTarefa("Título antigo", "desc antiga");

        Task editada = service.editarTarefa(task.getId(), "Título novo", "desc nova");

        assertEquals("Título novo", editada.getTitulo());
        assertEquals("desc nova", editada.getDescricao());
    }

    @Test
    void deveRemoverTarefa() {
        Task task = service.criarTarefa("Tarefa a remover", "desc");

        service.removerTarefa(task.getId());

        assertThrows(TaskNotFoundException.class, () -> service.buscarTarefa(task.getId()));
    }

    @Test
    void deveLancarExcecaoAoRemoverTarefaInexistente() {
        assertThrows(TaskNotFoundException.class, () -> service.removerTarefa(999L));
    }
}