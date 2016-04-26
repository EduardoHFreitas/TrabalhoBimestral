package br.univel.interfaces;

import java.util.List;
import br.univel.view.Cliente;

public interface Funcoes <obj, id> {

	public void salvar(Object obj, int id);

	public obj buscar(Object obj, int id);

	public void atualizar(Object obj, int id);

	public void excluir(Object obj, int id);

	public List<Cliente> listarTodos();

}
