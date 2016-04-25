package br.univel.interfaces;

import java.util.List;
import br.univel.view.Cliente;

public interface Funcoes <obj, id> {

	public void salvar(obj id);

	public obj buscar(obj id);

	public void atualizar(obj id);

	public void excluir(obj id);

	public List<Cliente> listarTodos();

}
