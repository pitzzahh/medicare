/*
 * MIT License
 *
 * Copyright (c) 2023 pitzzahh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.pitzzahh.medicare.backend.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.IOException;
import java.io.File;

/**
 * class used by services to gain access to the database.
 */
public class DatabaseConnection {

    private String driverClassName;
    private String url;
    private static final String APP_DATA_DIR = System.getenv("LOCALAPPDATA") + "\\Programs";
    private static final File DIR = new File(APP_DATA_DIR, "Medicare");

    private static JdbcTemplate jdbcTemplate;

    /**
     * Sets the driver class name.
     * <p>Examples:</p>
     * <p>postgres: org.postgresql.Driver</p>
     * <p>mysql: com.mysql.cj.jdbc.Driver</p>
     * @param driverClassName the driver class name
     * @return a {@code DatabaseConnection} object.
     */
    public DatabaseConnection setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    /**
     * Sets the url of the database.
     * <p>Examples:</p>
     * <p>postgres: jdbc:postgresql://localhost/{@code <name of the database>}</p>
     * <p>mysql: jdbc:mysql://host1:33060/{@code <name of the database>}</p>
     * @param url the url to the database.
     * @return a {@code DatabaseConnection} object.
     */
    public DatabaseConnection setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Creates a datasource
     */
    public void setDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Returns a {@code JdbcTemplate} object.
     * Checks first if the {@code JdbcTemplate} object is null. If it is null,
     * throws a {@code RuntimeException}.
     * @return a {@code JdbcTemplate} object.
     */
    public static JdbcTemplate getJDBC() {
        if (jdbcTemplate == null) {throw new RuntimeException("JDBC is not initialized");}
        return jdbcTemplate;
    }

    public static void createDatabaseFile() throws IOException {
        String appDataPath = System.getenv("LOCALAPPDATA") + "\\Programs";

        File dir = new File(appDataPath, "MEDiCARE");
        if (!dir.exists()) {
            System.out.println("Create folder MEDiCARE = " + dir.mkdir());
        }
        System.out.println("Create Database: " + new File(dir, "data.db").createNewFile());
    }

    public static File getDatabaseFile() {
        String appDataPath = System.getenv("LOCALAPPDATA") + "\\Programs";
        File dir = new File(appDataPath, "MEDiCARE");
        return new File(dir, "data.db");
    }

    public static boolean doesNotExist() {
        return !new File(DIR, "data.db").exists();
    }

    // exposing tables
    public static void createTables() {
        getJDBC().execute("CREATE TABLE IF NOT EXISTS l0g1n " +
                "(  " +
                "    u$erN4me TEXT NOT NULL," +
                "    p$ssW0rd TEXT NOT NULL," +
                "    PRIMARY KEY (u$erN4me)" +
                ");");
        getJDBC().execute("CREATE TABLE IF NOT EXISTS p4t13nt$ (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,\n" +
                "    last_name TEXT  NOT NULL,\n" +
                "    first_name TEXT NOT NULL,\n" +
                "    middle_name TEXT,\n" +
                "    gender TEXT NOT NULL,\n" +
                "    birthdate TEXT NOT NULL,\n" +
                "    address TEXT NOT NULL,\n" +
                "    phone_number TEXT,\n" +
                "    doctor_id TEXT,\n" +
                "    doctor_name TEXT,\n" +
                "    doctor_specialization TEXT,\n" +
                "    date_confined TEXT,\n" +
                "    symptoms TEXT NOT NULL\n" +
                ");");
        getJDBC().execute("CREATE TABLE IF NOT EXISTS d0ct0r$ (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,\n" +
                "    last_name TEXT  NOT NULL,\n" +
                "    first_name TEXT NOT NULL,\n" +
                "    middle_name TEXT,\n" +
                "    gender TEXT NOT NULL,\n" +
                "    birthdate TEXT NOT NULL,\n" +
                "    address TEXT NOT NULL,\n" +
                "    phone_number TEXT,\n" +
                "    specialization TEXT NOT NULL\n" +
                ");\n");
        getJDBC().execute("CREATE TABLE IF NOT EXISTS d1sch4rg3d_p4t13nt$ (\n" +
                "    id INTEGER PRIMARY KEY NOT NULL UNIQUE,\n" +
                "    patient_name TEXT  NOT NULL,\n" +
                "    name_of_doctor TEXT NOT NULL,\n" +
                "    date_disconfined TEXT,\n" +
                "    date_discharged TEXT NOT NULL\n" +
                ");");
        getJDBC().execute("INSERT OR IGNORE INTO l0g1n(u$erN4me, p$ssW0rd) VALUES ('YWRtaW4=', 'YWRtaW4=');");
    }

}

