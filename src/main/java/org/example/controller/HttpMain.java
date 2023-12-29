package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lihui
 * @date 2023-12-28
 */
@Slf4j
public class HttpMain {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(100, 100, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.DiscardPolicy());
        AtomicLong atomicLong = new AtomicLong();
        while (true) {
            executorService.submit(() -> {
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    String url = String.format("http://127.0.0.1:8080/hello?i=%s", atomicLong.addAndGet(1));
                    String response = restTemplate.getForObject(url, String.class);
                    System.out.println(String.format("url=%s, res=%s", url, response));
                } catch (Exception e) {
                    log.error("error {}", e.getMessage(), e);
                    System.exit(1);
                }
            });
        }
    }
}
