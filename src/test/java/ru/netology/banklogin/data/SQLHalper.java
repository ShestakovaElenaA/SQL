package ru.netology.banklogin.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHalper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHalper(){
    }

    private static Connection getCoon() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"),"app","pass");
    }
    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode(){
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getCoon();
        var code = runner.query(conn,codeSQL,new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static void cleanDatabase(){
        var connection = getCoon();
        runner.execute(connection,"DELETE FROM auth_codes");
        runner.execute(connection,"DELETE FROM card_transactions");
        runner.execute(connection,"DELETE FROM cards");
        runner.execute(connection,"DELETE FROM users");
    }
    @SneakyThrows
    public static void cleanAuthCodes() {
        var connection = getCoon();
        runner.execute(connection,"DELETE FROM auth_codes");
    }
}
