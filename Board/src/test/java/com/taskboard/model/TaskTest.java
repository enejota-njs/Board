
package com.taskboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void deveCriarTarefaComStatusInicialTodo() {
        Task task = new Task(1L, "Estudar Java", "Revisar POO");

        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals("Estudar Java", task.getTitulo());
    }

    @Test
    void naoDeveCriarTarefaComTituloVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Task(1L, "", "descrição"));
    }

    @Test
    void deveAvancarStatusCorretamente() {
        Task task = new Task(1L, "Tarefa", "desc");

        task.avancarStatus();
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());

        task.avancarStatus();
        assertEquals(TaskStatus.DONE, task.getStatus());
    }

    @Test
    void naoDeveAvancarStatusAlemDeConcluida() {
        Task task = new Task(1L, "Tarefa", "desc");
        task.avancarStatus();
        task.avancarStatus();

        assertThrows(IllegalStateException.class, task::avancarStatus);
    }

    @Test
    void deveAtualizarTituloEDescricao() {
        Task task = new Task(1L, "Título antigo", "desc antiga");

        task.atualizarTitulo("Título novo");
        task.atualizarDescricao("desc nova");

        assertEquals("Título novo", task.getTitulo());
        assertEquals("desc nova", task.getDescricao());
    }
}