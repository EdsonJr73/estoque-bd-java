package cadastrobd.model.util;

/**
 *
 * @author edson-202308892185
 */
import java.sql.*;

public class SequenceManager {

    public static int getValue(String sequenceName, Connection conn) throws SQLException {
        String sql = "SELECT NEXT VALUE FOR " + sequenceName + " AS next_val";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_val");
            } else {
                throw new SQLException("Não foi possível obter o próximo valor da sequência: " + sequenceName);
            }
        }
    }
}
