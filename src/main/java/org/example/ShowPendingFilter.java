package org.example;

import java.util.List;

public class ShowPendingFilter implements FilterStrategy {
    public List<Tarefa> filtrar(List<Tarefa> tarefas) {
        return tarefas.stream().filter(t -> !t.isConcluida()).toList();
    }
}
