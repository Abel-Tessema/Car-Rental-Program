package file_manager;

import utility.Directory;
import file_manager.errorMessage.ERROR;

import java.util.HashMap;
import java.util.Map;

public class SchemaId {
    private final HashMap<String, Map<String, Integer>> tables;
    private final DirectoryManager directoryManager = new DirectoryManager();
    private final Stream<HashMap<String, Map<String, Integer>>> stream = new Stream<>();

    public SchemaId() {
        tables = stream.reader(Directory.SchemaIdsPath);
    }
    public SchemaId(HashMap<String, Map<String, Integer>> tables) {
        this.tables = tables;
        updateSchema();
    }

    public void updateSchema() {
        stream.writer(tables, Directory.SchemaIdsPath);
    }

    public void addTable(String schemaTitle) {
        if (tables.containsKey(schemaTitle)) {
            ERROR e = new ERROR(5000, "Schema name already existed.");
            e.print();
            return;
        }

        Map<String, Integer> idAndSize = new HashMap<>();

        idAndSize.put("id",0); // For id numbers which get auto-incremented during data-insertion
        idAndSize.put("size",0); // For size of a given table which gets auto-incremented during data-insertion and decremented during data-deletion

        tables.put(schemaTitle, idAndSize);

        directoryManager.createDirectory(Directory.TableDirectory + schemaTitle);

        updateSchema();
    }

    public void removeTable(String schemaTitle) {
        tables.remove(schemaTitle);
        directoryManager.deleteDirectory(Directory.TableDirectory + schemaTitle);

        updateSchema();
    }

    public void resetDatabase() {
        Map<String, Integer> zeros = new HashMap<>();
        zeros.put("id",0);
        zeros.put("size",0);
        for (var table : tables.entrySet()) {
            tables.put(table.getKey(), zeros);
            directoryManager.cleanDirectory(Directory.TableDirectory + table.getKey());
        }
        updateSchema();
    }

    private Map<String, Integer> schemaDetail(String schemaTitle) {
        if (tables.containsKey(schemaTitle))
            return tables.get(schemaTitle);
        else {
            ERROR e = new ERROR(4000, "table no found");
            e.print();
            return null;
        }
    }

    public void incrementSize(String schemaTitle) {
        Map<String, Integer> i = schemaDetail(schemaTitle);

        if (i == null) return;

        i.put("id", i.get("id") + 1);
        i.put("size", i.get("size") + 1);

        tables.put(schemaTitle, i);
        updateSchema();
    }

    public void decrementSize(String schemaTitle) {
        Map<String, Integer> i = schemaDetail(schemaTitle);

        if (i == null) return;

        i.put("size", i.get("size") - 1);

        tables.put(schemaTitle, i);
        updateSchema();
    }

    public int getTableLatestId(String schemaTitle) {
        return tables.get(schemaTitle).get("id");
    }

    public void display() {
        System.out.println("=".repeat(40));
        System.out.printf("%-15s%-7s%-7s\n", "Table", "ID", "Size");

        for (var entry : tables.entrySet()) {
            String tableName = entry.getKey();
            Map<String, Integer> innerMap = entry.getValue();

            System.out.printf("%-15s%-7s%-7s\n", tableName, innerMap.get("id"), innerMap.get("size"));

        }
        System.out.println("=".repeat(40));
    }
}
