package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TaskTableModel extends AbstractTableModel implements TaskObserver {
    private transient List<Tarefa> tarefasVisiveis;
    private transient FilterStrategy filtro = new ShowAllFilter();

    public TaskTableModel() {
        TaskManager.getInstance().addObserver(this);
        atualizarTarefas();
    }

    public void setFiltro(FilterStrategy filtro) {
        this.filtro = filtro;
        atualizarTarefas();
    }

    private void atualizarTarefas() {
        tarefasVisiveis = TaskManager.getInstance().getTarefas(filtro);
        fireTableDataChanged();
    }

    public Tarefa getTaskAt(int row) {
        return tarefasVisiveis.get(row);
    }

    @Override
    public int getRowCount() {
        return tarefasVisiveis.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Tarefa tarefa = tarefasVisiveis.get(row);
        return col == 0 ? tarefa.isConcluida() : tarefa.getDescricao();
    }

    @Override
    public void setValueAt(Object aValue, int row, int col) {
        if (col == 0) {
            TaskManager.getInstance().mudaEstadoTarefa(getTaskAt(row));
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0;
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return col == 0 ? Boolean.class : String.class;
    }

    @Override
    public String getColumnName(int col) {
        return col == 0 ? "Concluída" : "Tarefa";
    }

    @Override
    public void onTaskListChanged() {
        atualizarTarefas();
    }
}
