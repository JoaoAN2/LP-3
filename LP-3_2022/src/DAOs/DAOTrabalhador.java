package DAOs;

import Entidades.Trabalhador;
import java.util.ArrayList;
import java.util.List;

public class DAOTrabalhador extends DAOGenerico<Trabalhador> {

    public DAOTrabalhador() {
        super(Trabalhador.class);
    }

    public List<Trabalhador> listByCpf(String cpf) {
        return em.createQuery("SELECT e FROM Trabalhador e WHERE e.cpf LIKE :cpf").setParameter("cpf", "%" + cpf + "%").getResultList();
    }

    public List<Trabalhador> listInOrderCpf() {
        return em.createQuery("SELECT e FROM Trabalhador e ORDER BY e.cpf").getResultList();
    }

    public List<Trabalhador> listInOrderAposentado() {
        return em.createQuery("SELECT e FROM Trabalhador e ORDER BY e.aposentado").getResultList();
    }

    public List<Trabalhador> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Trabalhador e WHERE e.nome LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Trabalhador> listInOrderNome() {
        return em.createQuery("SELECT e FROM Trabalhador e ORDER BY e.nome").getResultList();
    }

    public List<Trabalhador> listInOrderSalario() {
        return em.createQuery("SELECT e FROM Trabalhador e ORDER BY e.salario").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Trabalhador> lf;
        if (order.equals("cpf")) {
            lf = listInOrderCpf();
        } else if (order.equals("aposentado")) {
            lf = listInOrderAposentado();
        } else if (order.equals("nome")) {
            lf = listInOrderNome();
        } else if (order.equals("salario")) {
            lf = listInOrderSalario();
        } else {
            lf = listInOrderCpf();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getCpf() + "-" + lf.get(i).getAposentado());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOTrabalhador daoTrabalhador = new DAOTrabalhador();
        List<Trabalhador> listaTrabalhador = daoTrabalhador.list();
        for (Trabalhador trabalhador : listaTrabalhador) {
            System.out.println(trabalhador);
        }
    }
}
