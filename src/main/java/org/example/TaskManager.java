package org.example;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static TaskManager instance;
    private final List<Tarefa> tarefas = new ArrayList<>();
    private final List<TaskObserver> observers = new ArrayList<>();

    private TaskManager() {}

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void addObserver(TaskObserver obs) {
        observers.add(obs);
    }

    private void notifyObservers() {
        for (TaskObserver obs : observers) {
            obs.onTaskListChanged();
        }
    }

    public void adTarefa(String desc) {
        tarefas.add(new Tarefa(desc));
        notifyObservers();
    }

    public void mudaEstadoTarefa(Tarefa tarefa) {
        tarefa.setConcluida(!tarefa.isConcluida());
        notifyObservers();
    }

    public void removeTarefasCompletas() {
        tarefas.removeIf(Tarefa::isConcluida);
        notifyObservers();
    }

    public List<Tarefa> getTarefas(FilterStrategy filter) {
        return filter.filtrar(tarefas);
    }

    public void salvarTarefas(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Tarefa tarefa : tarefas) {
                String status = tarefa.isConcluida() ? "s" : "n";
                writer.write(status + " " + tarefa.getDescricao());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Ocorreu um erro ao salvar tarefas no disco.",
                    "Erro ao Salvar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void carregarTarefas(File file) {
        if (!file.exists()) return;

        List<Tarefa> carregadas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                boolean concluida = linha.startsWith("s");
                String descricao = linha.substring(1).trim();
                carregadas.add(new Tarefa(descricao, concluida));
            }

            tarefas.clear();
            tarefas.addAll(carregadas);
            notifyObservers();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Ocorreu um erro ao carregar tarefas do disco.",
                    "Erro ao Carregar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
