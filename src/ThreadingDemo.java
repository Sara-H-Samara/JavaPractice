import java.util.concurrent.*;

public class ThreadingDemo {

    public static void main(String[] args) {

        System.out.println("========== JAVA THREADING DEMO ==========\n");

        // =========================================================
        // 1. BASIC THREAD
        // =========================================================

        System.out.println("1. BASIC THREAD");

        Thread thread1 = new Thread(() -> {
            System.out.println(
                    "Thread is running: " +
                            Thread.currentThread().getName()
            );
        });

        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();


        // =========================================================
        // 2. RUNNABLE
        // =========================================================

        System.out.println("2. RUNNABLE");

        Runnable task = () -> {
            System.out.println(
                    "Runnable task executed by: " +
                            Thread.currentThread().getName()
            );
        };

        Thread thread2 = new Thread(task);

        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();


        // =========================================================
        // 3. EXECUTOR SERVICE
        // =========================================================

        System.out.println("3. EXECUTOR SERVICE");

        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        Runnable task1 = () -> {
            System.out.println(
                    "Task 1 executed by " +
                            Thread.currentThread().getName()
            );
        };

        Runnable task2 = () -> {
            System.out.println(
                    "Task 2 executed by " +
                            Thread.currentThread().getName()
            );
        };

        Runnable task3 = () -> {
            System.out.println(
                    "Task 3 executed by " +
                            Thread.currentThread().getName()
            );
        };

        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);


        // =========================================================
        // 4. SUBMIT + CALLABLE + FUTURE
        // =========================================================

        System.out.println("\n4. CALLABLE + FUTURE");

        Callable<Integer> calculation = () -> {

            System.out.println(
                    "Calculating using: " +
                            Thread.currentThread().getName()
            );

            Thread.sleep(1000);

            return 100 + 200;
        };

        Future<Integer> future =
                executor.submit(calculation);

        try {

            Integer result = future.get();

            System.out.println(
                    "Calculation result = " + result
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {

            System.out.println(
                    "Task failed: " + e.getMessage()
            );
        }


        // =========================================================
        // 5. SYNCHRONIZED BANK ACCOUNT
        // =========================================================

        System.out.println("\n5. SYNCHRONIZED BANK ACCOUNT");

        BankAccount account =
                new BankAccount(1000);


        // Deposit task
        Runnable depositTask = () -> {

            for (int i = 0; i < 5; i++) {

                account.deposit(100);

                sleep(100);
            }
        };


        // Withdraw task
        Runnable withdrawTask = () -> {

            for (int i = 0; i < 5; i++) {

                account.withdraw(50);

                sleep(100);
            }
        };


        executor.submit(depositTask);
        executor.submit(withdrawTask);


        // =========================================================
        // 6. SYNCHRONIZED BLOCK
        // =========================================================

        Runnable balanceTask = () -> {

            for (int i = 0; i < 3; i++) {

                int balance = account.getBalance();

                System.out.println(
                        "Balance checked by " +
                                Thread.currentThread().getName() +
                                " = " +
                                balance
                );

                sleep(150);
            }
        };

        executor.submit(balanceTask);


        // =========================================================
        // 7. MORE CONCURRENT TASKS
        // =========================================================

        for (int i = 1; i <= 5; i++) {

            final int taskNumber = i;

            executor.submit(() -> {

                System.out.println(
                        "Processing business task " +
                                taskNumber +
                                " by " +
                                Thread.currentThread().getName()
                );

                sleep(500);

                System.out.println(
                        "Business task " +
                                taskNumber +
                                " finished"
                );
            });
        }


        // =========================================================
        // 8. SHUTDOWN EXECUTOR
        // =========================================================

        executor.shutdown();

        try {

            boolean finished =
                    executor.awaitTermination(
                            10,
                            TimeUnit.SECONDS
                    );

            if (finished) {

                System.out.println(
                        "\nAll tasks finished."
                );

            } else {

                System.out.println(
                        "\nSome tasks are still running."
                );
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "Main thread was interrupted."
            );
        }


        // =========================================================
        // FINAL BALANCE
        // =========================================================

        System.out.println(
                "\nFinal balance = " +
                        account.getBalance()
        );

        System.out.println(
                "\n========== PROGRAM FINISHED =========="
        );
    }


    // =============================================================
    // HELPER METHOD
    // =============================================================

    private static void sleep(long milliseconds) {

        try {

            Thread.sleep(milliseconds);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }


    // =============================================================
    // BANK ACCOUNT
    // =============================================================

    static class BankAccount {

        private int balance;


        public BankAccount(int balance) {

            this.balance = balance;
        }


        // =========================================================
        // SYNCHRONIZED METHOD
        // =========================================================

        public synchronized void deposit(int amount) {

            System.out.println(
                    Thread.currentThread().getName() +
                            " depositing " +
                            amount
            );

            balance += amount;

            System.out.println(
                    "Balance after deposit = " +
                            balance
            );
        }


        // =========================================================
        // SYNCHRONIZED METHOD
        // =========================================================

        public synchronized void withdraw(int amount) {

            System.out.println(
                    Thread.currentThread().getName() +
                            " withdrawing " +
                            amount
            );

            if (balance >= amount) {

                balance -= amount;

                System.out.println(
                        "Balance after withdraw = " +
                                balance
                );

            } else {

                System.out.println(
                        "Not enough money!"
                );
            }
        }


        // =========================================================
        // SYNCHRONIZED BLOCK
        // =========================================================

        public int getBalance() {

            synchronized (this) {

                return balance;
            }
        }
    }
}

