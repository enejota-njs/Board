package com.taskboard.controller;

import com.taskboard.exception.TaskNotFoundException;
import com.taskboard.model.Task;
import com.taskboard.model.TaskStatus;
import com.taskboard.repository.InMemoryTaskRepository;
import com.taskboard.repository.TaskRepository;
import com.taskboard.service.TaskService;
import com.taskboard.service.TaskServiceImpl;

import java.util.List;
import java.util.Scanner;

/**
 * Camada de apresentação (interface via console).
 * Responsável apenas por interagir com o usuário e delegar
 * toda a regra de negócio para a camada de service.
 */
public class TaskBoardApp {

    private final TaskService taskService;
    private final Scanner scanner = new Scanner(System.in);

    public TaskBoardApp(TaskService taskService) {
        this.taskService = taskService;
    }

    public static void main(String[] args) {
        TaskRepository repository = new InMemoryTaskRepository();
        TaskService service = new TaskServiceImpl(repository);
        TaskBoardApp app = new TaskBoardApp(service);
        app.iniciar();
    }

    public void iniciar() {
        System.out.println("=== Board de Tarefas ===");
        boolean rodando = true;

        while (rodando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            try {
                switch (opcao) {
                    case "1" -> criarTarefa();
                    case "2" -> listarTodas();
                    case "3" -> listarPorStatus();
                    case "4" -> avancarStatus();
                    case "5" -> editarTarefa();
                    case "6" -> removerTarefa();
                    case "0" -> {
                        rodando = false;
                        System.out.println("Encerrando o board. Até logo!");
                    }
                    default -> System.out.println("Opção inválida, tente novamente.");
                }
            } catch (TaskNotFoundException | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("""

                --------------------------------
                1. Criar tarefa
                2. Listar todas as tarefas
                3. Listar tarefas por status
                4. Avançar status de uma tarefa
                5. Editar tarefa
                6. Remover tarefa
                0. Sair
                --------------------------------
                Escolha uma opção:""");
    }

    private void criarTarefa() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        Task task = taskService.criarTarefa(titulo, descricao);
        System.out.println("Tarefa criada com sucesso: " + task);
    }

    private void listarTodas() {
        List<Task> tasks = taskService.listarTodas();
        imprimirTarefas(tasks);
    }

    private void listarPorStatus() {
        TaskStatus status = lerStatus();
        List<Task> tasks = taskService.listarPorStatus(status);
        imprimirTarefas(tasks);
    }

    private void avancarStatus() {
        Long id = lerId();
        Task task = taskService.avancarStatus(id);
        System.out.println("Tarefa atualizada: " + task);
    }

    private void editarTarefa() {
        Long id = lerId();
        System.out.print("Novo título: ");
        String novoTitulo = scanner.nextLine();
        System.out.print("Nova descrição: ");
        String novaDescricao = scanner.nextLine();

        Task task = taskService.editarTarefa(id, novoTitulo, novaDescricao);
        System.out.println("Tarefa editada: " + task);
    }

    private void removerTarefa() {
        Long id = lerId();
        taskService.removerTarefa(id);
        System.out.println("Tarefa removida com sucesso.");
    }

    private void imprimirTarefas(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    private Long lerId() {
        System.out.print("Informe o ID da tarefa: ");
        return Long.parseLong(scanner.nextLine().trim());
    }

    private TaskStatus lerStatus() {
        System.out.println("Status: 1-A Fazer | 2-Em Andamento | 3-Concluída");
        System.out.print("Escolha: ");
        String opcao = scanner.nextLine().trim();
        return switch (opcao) {
            case "1" -> TaskStatus.TODO;
            case "2" -> TaskStatus.IN_PROGRESS;
            case "3" -> TaskStatus.DONE;
            default -> throw new IllegalArgumentException("Status inválido");
        };
    }
}