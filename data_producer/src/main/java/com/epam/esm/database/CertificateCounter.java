package com.epam.esm.database;

import java.sql.SQLException;

public interface CertificateCounter {
    long getCertificatesCount() throws SQLException;

    void setUrl(String url);

    void setUser(String user);

    void setPassword(String password);
}
