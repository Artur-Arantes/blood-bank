package br.com.blood.bank;

import org.testcontainers.containers.MySQLContainer;

public class BloodBankDataBaseContainer extends MySQLContainer<BloodBankDataBaseContainer> {

    private static final String MYSQL_VERSION = "mysql:8.0";
    private static final String APP_NAME = "blood_bank";
    private static BloodBankDataBaseContainer container;



    private boolean isActive;

    public static BloodBankDataBaseContainer getInstance() {
        return getInstance(true);
    }

    public static BloodBankDataBaseContainer getInstance(final boolean isActive) {
        if (container == null) {
            container = new BloodBankDataBaseContainer(isActive);
        }
        return container;
    }

    private BloodBankDataBaseContainer(final boolean isActive) {
        super(MYSQL_VERSION);
        this.isActive = isActive;
        this.withUsername(APP_NAME)
                .withDatabaseName(APP_NAME)
                .withPassword(APP_NAME)
                .withReuse(false);
    }

    @Override
    public void start() {
        if (isActive) {
            super.start();
            System.setProperty("DB_URL", container.getJdbcUrl());
            System.setProperty("DB_USER", container.getUsername());
            System.setProperty("DB_PASS", container.getPassword());
        }
    }

    @Override
    public void stop() {
    }
}
