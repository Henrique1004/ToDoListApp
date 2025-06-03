package org.example;

import java.util.ArrayList;
import java.util.List;

public class ShowAllFilter implements FilterStrategy {
    public List<Tarefa> filtrar(List<Tarefa> tarefas) {
        return new ArrayList<>(tarefas);
    }
}
