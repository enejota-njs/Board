package com.taskboard.model;

/**
 * Representa as etapas possíveis de uma tarefa dentro do board.
 * O board segue o fluxo clássico: A FAZER -> EM ANDAMENTO -> CONCLUÍDA.
 */
public enum TaskStatus {
    TODO("A Fazer"),
    IN_PROGRESS("Em Andamento"),
    DONE("Concluída");

    private final String descricao;

    TaskStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}