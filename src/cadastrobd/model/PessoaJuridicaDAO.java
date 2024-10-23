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

public class PessoaJuridicaDAO {

    public PessoaJuridica getPessoa(int id) throws SQLException {
        String sql = "SELECT * FROM pessoa p JOIN pessoa_juridica pj ON p.idPessoa = pj.idPessoa WHERE p.idPessoa = ?";
        try (Connection conn = ConectorBD.getConnection(); PreparedStatement stmt = ConectorBD.getPrepared(conn, sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PessoaJuridica(
                        rs.getInt("idPessoa"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cnpj")
                );
            }
        }
        return null;
    }

    public List<PessoaJuridica> getPessoas() throws SQLException {
        List<PessoaJuridica> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoa p JOIN pessoa_juridica pj ON p.idPessoa = pj.idPessoa";
        try (Connection conn = ConectorBD.getConnection(); PreparedStatement stmt = ConectorBD.getPrepared(conn, sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pessoas.add(new PessoaJuridica(
                        rs.getInt("idPessoa"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cnpj")
                ));
            }
        }
        return pessoas;
    }

    public void incluir(PessoaJuridica pessoa) throws SQLException {
        String sqlPessoa = "INSERT INTO pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO pessoa_juridica (idPessoaJuridica, idPessoa, cnpj) VALUES (?, ?, ?)";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            int novoIdPessoa = SequenceManager.getValue("seq_pessoa_id", conn);
            pessoa.setId(novoIdPessoa);

            try (PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa); PreparedStatement stmtJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica)) {

                stmtPessoa.setInt(1, novoIdPessoa);
                stmtPessoa.setString(2, pessoa.getNome());
                stmtPessoa.setString(3, pessoa.getLogradouro());
                stmtPessoa.setString(4, pessoa.getCidade());
                stmtPessoa.setString(5, pessoa.getEstado());
                stmtPessoa.setString(6, pessoa.getTelefone());
                stmtPessoa.setString(7, pessoa.getEmail());
                stmtPessoa.executeUpdate();

                stmtJuridica.setInt(1, novoIdPessoa);
                stmtJuridica.setInt(2, novoIdPessoa);
                stmtJuridica.setString(3, pessoa.getCnpj());
                stmtJuridica.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void alterar(PessoaJuridica pessoa) throws SQLException {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        String sqlPessoaJuridica = "UPDATE pessoa_juridica SET cnpj = ? WHERE idPessoa = ?";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa); PreparedStatement stmtJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica)) {

                stmtPessoa.setString(1, pessoa.getNome());
                stmtPessoa.setString(2, pessoa.getLogradouro());
                stmtPessoa.setString(3, pessoa.getCidade());
                stmtPessoa.setString(4, pessoa.getEstado());
                stmtPessoa.setString(5, pessoa.getTelefone());
                stmtPessoa.setString(6, pessoa.getEmail());
                stmtPessoa.setInt(7, pessoa.getId());
                stmtPessoa.executeUpdate();

                stmtJuridica.setString(1, pessoa.getCnpj());
                stmtJuridica.setInt(2, pessoa.getId());
                stmtJuridica.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaJuridica = "DELETE FROM pessoa_juridica WHERE idPessoa = ?";
        String sqlPessoa = "DELETE FROM pessoa WHERE idPessoa = ?";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica); PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa)) {

                stmtJuridica.setInt(1, id);
                stmtJuridica.executeUpdate();

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
