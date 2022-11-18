package com.spring.student_management;

import com.spring.student_management.exceptions.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
@Slf4j
public class StudentManagementApplication {
    private static final String RESOURCES_ROOT = "student_management\\src\\main\\resources\\";
    private static final String DB_SCRIPTS_ROOT = RESOURCES_ROOT + "static\\DB\\";

    private static final String LOG_FILE = RESOURCES_ROOT + "static\\student_management.log";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/student_management";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    private static void resetLog() throws AppException {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE)) {
            fileWriter.write("");
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    private static void executeSqlFilesOnApplicationStarts() throws AppException {
        log.info("------------------Trying to run sql scripts automatically");
        final String tables = String.format("%s001_CREATE_TABLES.sql", DB_SCRIPTS_ROOT);
        final String roleInserts = String.format("%s002_INSERTS.sql", DB_SCRIPTS_ROOT);
        try (final BufferedReader readTables = new BufferedReader(new FileReader(tables));
             final BufferedReader readRoleInserts = new BufferedReader(new FileReader(roleInserts));
             final Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        ) {
            final ScriptRunner sr = new ScriptRunner(connection);
            runScripts(sr, readTables, readRoleInserts);
            log.info("All scripts has been successfully run!");
        } catch (Exception e) {
            log.error("Unexpected error occurred trying to run the scripts. Try to run them manually!", e);
            throw new AppException(e);
        }
    }

    private static void runScripts(ScriptRunner sr, Reader... readers) {
        for (Reader reader : readers) sr.runScript(reader);
    }

    public static void main(String[] args) {
        try {
//            resetLog(); // comment this if you don't want to reset the logs every time the application starts
            executeSqlFilesOnApplicationStarts(); // comment this if you don't want to run the scripts automatically
        } catch (Exception e) {
            log.error("Unexpected error occurred: ", e);
        }
        SpringApplication.run(StudentManagementApplication.class, args);
    }

}
