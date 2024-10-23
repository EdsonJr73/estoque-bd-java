package cadastrobd;

/**
 *
 * @author edson-202308892185
 */
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;

import java.util.List;

public class CadastroBDTeste {

    public static void main(String[] args) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        try {
            PessoaFisica pf = new PessoaFisica(0, "Edson Luiz", "Rua A", "Goiania", "GO", "99999-9999", "edson@gmail.com", "123.456.789-00");
            pessoaFisicaDAO.incluir(pf);

            pf.setCpf("987.654.321-00");
            pessoaFisicaDAO.alterar(pf);

            List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
            pessoasFisicas.forEach(PessoaFisica::exibir);

            pessoaFisicaDAO.excluir(pf.getId());

            PessoaJuridica pj = new PessoaJuridica(0, "Galaxy", "Rua B", "Sao Paulo", "SP", "88888-8888", "contato@galaxy.com", "12.345.678/0001-99");
            pessoaJuridicaDAO.incluir(pj);

            pj.setCnpj("98.765.432/0001-11");
            pessoaJuridicaDAO.alterar(pj);

            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
            pessoasJuridicas.forEach(PessoaJuridica::exibir);

            pessoaJuridicaDAO.excluir(pj.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
