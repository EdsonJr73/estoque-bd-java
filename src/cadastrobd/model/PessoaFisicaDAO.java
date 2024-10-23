package cadastrobd.model;

/**
 *
 * @author edson-202308892185
 */
import cadastrobd.model.util.ConectorBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import cadastrobd.model.util.SequenceManager;

public class PessoaFisicaDAO {

    public PessoaFisica getPessoa(int id) throws SQLException {
        String sql = "SELECT * FROM pessoa p JOIN pessoa_fisica pf ON p.idPessoa = pf.idPessoa WHERE p.idPessoa = ?";
        try (Connection conn = ConectorBD.getConnection(); PreparedStatement stmt = ConectorBD.getPrepared(conn, sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PessoaFisica(
                        rs.getInt("idPessoa"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                );
            }
        }
        return null;
    }

    public List<PessoaFisica> getPessoas() throws SQLException {
        List<PessoaFisica> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoa p JOIN pessoa_fisica pf ON p.idPessoa = pf.idPessoa";
        try (Connection conn = ConectorBD.getConnection(); PreparedStatement stmt = ConectorBD.getPrepared(conn, sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pessoas.add(new PessoaFisica(
                        rs.getInt("idPessoa"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                ));
            }
        }
        return pessoas;
    }

    public void incluir(PessoaFisica pessoa) throws SQLException {
        String sqlPessoa = "INSERT INTO pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO pessoa_fisica (idPessoaFisica, idPessoa, cpf) VALUES (?, ?, ?)";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            int novoIdPessoa = SequenceManager.getValue("seq_pessoa_id", conn);
            pessoa.setId(novoIdPessoa);

            try (PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa); PreparedStatement stmtFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica)) {

                stmtPessoa.setInt(1, novoIdPessoa);
                stmtPessoa.setString(2, pessoa.getNome());
                stmtPessoa.setString(3, pessoa.getLogradouro());
                stmtPessoa.setString(4, pessoa.getCidade());
                stmtPessoa.setString(5, pessoa.getEstado());
                stmtPessoa.setString(6, pessoa.getTelefone());
                stmtPessoa.setString(7, pessoa.getEmail());
                stmtPessoa.executeUpdate();

                stmtFisica.setInt(1, novoIdPessoa);
                stmtFisica.setInt(2, novoIdPessoa);
                stmtFisica.setString(3, pessoa.getCpf());
                stmtFisica.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void alterar(PessoaFisica pessoa) throws SQLException {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        String sqlPessoaFisica = "UPDATE pessoa_fisica SET cpf = ? WHERE idPessoa = ?";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa); PreparedStatement stmtFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica)) {

                stmtPessoa.setString(1, pessoa.getNome());
                stmtPessoa.setString(2, pessoa.getLogradouro());
                stmtPessoa.setString(3, pessoa.getCidade());
                stmtPessoa.setString(4, pessoa.getEstado());
                stmtPessoa.setString(5, pessoa.getTelefone());
                stmtPessoa.setString(6, pessoa.getEmail());
                stmtPessoa.setInt(7, pessoa.getId());
                stmtPessoa.executeUpdate();

                stmtFisica.setString(1, pessoa.getCpf());
                stmtFisica.setInt(2, pessoa.getId());
                stmtFisica.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaFisica = "DELETE FROM pessoa_fisica WHERE idPessoa = ?";
        String sqlPessoa = "DELETE FROM pessoa WHERE idPessoa = ?";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica); PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa)) {

                stmtFisica.setInt(1, id);
                stmtFisica.executeUpdate();

                stmtPessoa.setInt(1, id);
                stmtPessoa.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
