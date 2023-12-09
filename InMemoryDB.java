import java.util.HashMap;

public class InMemoryDB {
    private HashMap<String, Integer> data;
    private HashMap<String, Integer> transaction;

    public InMemoryDB() {
        this.data = new HashMap<>();
        this.transaction = null;
    }

    public void begin_transaction() {
        if (this.transaction != null) {
            throw new IllegalStateException("A transaction is already in progress");
        }
        this.transaction = new HashMap<>();
    }

    public void put(String key, Integer value) {
        if (this.transaction == null) {
            throw new IllegalStateException("No transaction in progress");
        }
        this.transaction.put(key, value);
    }

    public Integer get(String key) {
        return this.data.get(key);
    }

    public void commit() {
        if (this.transaction == null) {
            throw new IllegalStateException("No transaction to commit");
        }
        this.data.putAll(this.transaction);
        this.transaction = null;
    }

    public void rollback() {
        if (this.transaction == null) {
            throw new IllegalStateException("No transaction to rollback");
        }
        this.transaction = null;
    }
}