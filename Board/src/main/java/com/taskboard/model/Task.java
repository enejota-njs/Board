package com.taskboard.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade que representa uma tarefa do board.
 * Mantém encapsulamento: os campos são privados e alterados apenas
 * através de métodos controlados.
 */
public class Task {

    private final Long id;
    private String titulo;
    private String descricao;
    private TaskStatus status;
    private final LocalDateTime criadaEm;
    private LocalDateTime atualizadaEm;

    public Task(Long id, String titulo, String descricao) {
        this.id = Objects.requireNonNull(id, "id não pode ser nulo");
        this.titulo = validarTitulo(titulo);
        this.descricao = descricao == null ? "" : descricao;
        this.status = TaskStatus.TODO;
        this.criadaEm = LocalDateTime.now();
        this.atualizadaEm = this.criadaEm;
    }

    private String validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título da tarefa não pode ser vazio");
        }
        return titulo;
    }

    public void atualizarTitulo(String novoTitulo) {
        this.titulo = validarTitulo(novoTitulo);
        this.atualizadaEm = LocalDateTime.now();
    }

    public void atualizarDescricao(String novaDescricao) {
        this.descricao = novaDescricao == null ? "" : novaDescricao;
        this.atualizadaEm = LocalDateTime.now();
    }

    public void avancarStatus() {
        switch (this.status) {
            case TODO -> this.status = TaskStatus.IN_PROGRESS;
            case IN_PROGRESS -> this.status = TaskStatus.DONE;
            case DONE -> throw new IllegalStateException("A tarefa já está concluída");
        }
        this.atualizadaEm = LocalDateTime.now();
    }

    public void alterarStatus(TaskStatus novoStatus) {
        this.status = Objects.requireNonNull(novoStatus, "status não pode ser nulo");
        this.atualizadaEm = LocalDateTime.now();
    }

    // ---------- Getters ----------

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public LocalDateTime getAtualizadaEm() {
        return atualizadaEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "#%d [%s] %s - %s".formatted(id, status.getDescricao(), titulo, descricao);
    }
}