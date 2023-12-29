package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihui
 * @date 2023-12-28
 */
@Slf4j
@RestController
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("/hello")
    public String hello(Long i) {
        try {
            log.info("req_id:{}", i);
            insertTable(i);
            return "hello_" + System.getenv("env") + "_" + i;
        } catch (Exception e) {
            log.error("error:{}", e.getMessage(), e);
        }
        return "error";
    }

    @RequestMapping("/long-process")
    public String pause() throws InterruptedException {
        System.out.println("Process started");
        Thread.sleep(30 * 1000);
        System.out.println("Process finished");
        return "Process finished";
    }

    private void insertTable(Long reqId) {
        String tableName = "table_" + System.getenv("env");
        String sql = String.format("insert into %s (req_id) values (?)", tableName);
        jdbcTemplate.update(sql, reqId);
    }
}
