package org.example;

import java.util.List;

public class ShowCompletedFilter implements FilterStrategy {
    public List<Tarefa> filtrar(List<Tarefa> tarefas) {
        return tarefas.stream().filter(Tarefa::isConcluida).toList();
    }
}
