package br.univel.interfaces;

import java.util.List;
import br.univel.view.Cliente;

public interface Funcoes <obj, id> {

	public void salvar(Object obj);

	public obj buscar(Object obj);

	public void atualizar(Object obj);

	public void excluir(Object obj);

	public List<Cliente> listarTodos(Object obj);

}
