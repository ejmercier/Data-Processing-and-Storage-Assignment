public class Main {
    public static void main(String[] args) {
        InMemoryDB inMemoryDB = new InMemoryDB();

        // should return null, because A doesn’t exist in the DB yet
        System.out.println(inMemoryDB.get("A"));

        try {
            // should throw an error because a transaction is not in progress
            inMemoryDB.put("A", 5);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        // starts a new transaction
        inMemoryDB.begin_transaction();

        // set’s value of A to 5, but its not committed yet
        inMemoryDB.put("A", 5);

        // should return null, because updates to A are not committed yet
        System.out.println(inMemoryDB.get("A"));

        // update A’s value to 6 within the transaction
        inMemoryDB.put("A", 6);

        // commits the open transaction
        inMemoryDB.commit();

        // should return 6, because updates to A are now committed
        System.out.println(inMemoryDB.get("A"));

        try {
            // throws an error, because there is no open transaction
            inMemoryDB.commit();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try{
            // throws an error because there is no ongoing transaction
            inMemoryDB.rollback();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }


        // should return null because B does not exist in the database
        System.out.println(inMemoryDB.get("B"));

        // starts a new transaction
        try {
            // should throw an error because a transaction is already in progress
            inMemoryDB.begin_transaction();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        // Set key B’s value to 10 within the transaction
        inMemoryDB.put("B", 10);

        // Rollback the transaction - revert any changes made to B
        inMemoryDB.rollback();

        // Should return null because changes to B were rolled back
        System.out.println(inMemoryDB.get("B"));

    }
}