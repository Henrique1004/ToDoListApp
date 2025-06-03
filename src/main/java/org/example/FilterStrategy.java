package org.example;

import java.util.List;

public interface FilterStrategy {
    List<Tarefa> filtrar(List<Tarefa> tarefas);
}
