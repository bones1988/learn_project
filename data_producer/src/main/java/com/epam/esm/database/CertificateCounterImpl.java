package com.epam.esm.database;

import java.sql.*;

public class CertificateCounterImpl implements CertificateCounter {
    private String url;
    private String user;
    private String password;
    private static final String COUNT_CERTIFICATES = "select count(*) from certificate";

    @Override
    public long getCertificatesCount() throws SQLException {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(COUNT_CERTIFICATES);
            long count = 0;
            while (rs.next()) {
                count = rs.getLong(1);
            }
            return count;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
