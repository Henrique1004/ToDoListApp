package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ToDoListApp extends JFrame {
    private final TaskTableModel tableModel = new TaskTableModel();
    private final JTable tabelaTarefas = new JTable(tableModel);
    private final JTextField campoNomeTarefa = new JTextField();

    public ToDoListApp() {
        setTitle("TodoApp");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TaskManager.getInstance().salvarTarefas(new File("tarefas.txt"));
                dispose();
            }
        });
        setLocationRelativeTo(null);
        inicializarUI();
    }

    private void inicializarUI() {
        JScrollPane scrollPane = new JScrollPane(tabelaTarefas);
        tabelaTarefas.setRowHeight(25);

        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoRemover = new JButton("Remover Concluídas");
        JButton botaoTodasTarefas = new JButton("Todas");
        JButton botaoTarefasConcluidas = new JButton("Concluídas");
        JButton botaoTarefasPendentes = new JButton("Pendentes");

        botaoAdicionar.addActionListener(e -> {
            String descricao = campoNomeTarefa.getText().trim();
            if (!descricao.isEmpty()) {
                TaskManager.getInstance().adTarefa(descricao);
                campoNomeTarefa.setText("");
            }
        });

        botaoRemover.addActionListener(e -> TaskManager.getInstance().removeTarefasCompletas());
        botaoTodasTarefas.addActionListener(e -> tableModel.setFiltro(new ShowAllFilter()));
        botaoTarefasConcluidas.addActionListener(e -> tableModel.setFiltro(new ShowCompletedFilter()));
        botaoTarefasPendentes.addActionListener(e -> tableModel.setFiltro(new ShowPendingFilter()));

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(campoNomeTarefa, BorderLayout.CENTER);
        inputPanel.add(botaoAdicionar, BorderLayout.EAST);

        JPanel filterPanel = new JPanel();
        filterPanel.add(botaoTodasTarefas);
        filterPanel.add(botaoTarefasConcluidas);
        filterPanel.add(botaoTarefasPendentes);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(botaoRemover, BorderLayout.WEST);
        bottomPanel.add(filterPanel, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        TaskManager.getInstance().carregarTarefas(new File("tarefas.txt"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoListApp().setVisible(true));
    }
}
