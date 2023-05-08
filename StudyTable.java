import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class StudyTable extends JFrame {


    // Helper method to convert a ResultSet to a DefaultTableModel
    public static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        // Get the number of columns in the result set
        int columnCount = metaData.getColumnCount();

        // Create a vector to hold the column names and data
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        // Add the column names to the vector
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnLabel(i));
        }

        // Add the data to the vector
        while (resultSet.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            data.add(row);
        }

        // Create a DefaultTableModel with the column names and data vectors
        return new DefaultTableModel(data, columnNames);
    }

}
