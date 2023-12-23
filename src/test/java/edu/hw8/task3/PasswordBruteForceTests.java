package edu.hw8.task3;

import edu.hw8.task2.FixedThreadPool;
import edu.hw8.task2.ThreadPool;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PasswordBruteForceTests {

    @NotNull private static ConcurrentMap<String, String> getLoginByHashConcurrentMap() {
        ConcurrentMap<String, String> loginByHash = new ConcurrentHashMap<>();
        loginByHash.put("d3d9446802a44259755d38e6d163e820", "1_stage");
        loginByHash.put("f899139df5e1059396431415e770c6dd", "2_stage");
        loginByHash.put("a9b7ba70783b617e9998dc4dd82eb3c5", "3_stage");
        loginByHash.put("b7a782741f667201b54880c925faec4b", "4_stage");
        loginByHash.put("33d6548e48d4318ceb0e3916a79afc84", "4.1_stage");
        loginByHash.put("d9798cdf31c02d86b8b81cc119d94836", "4.2_stage");
        loginByHash.put("5ecc613150de01b7e6824594426f24f4", "4.3_stage");
        loginByHash.put("7c77f048a2d02e784926184a82686fa0", "4.4_stage");
//        loginByHash.put("14ee22eaba297944c96afdbe5b16c65b", "5_stage");
//        loginByHash.put("03e6c61603f6c550ab49ab6a2d83f793", "5.1_stage");
//        loginByHash.put("c9077732a294f90a75acea3ce5f2a4e8", "5.2_stage");
//        loginByHash.put("1f2de15d680024fca36c47e16f5c95d2", "5.3_stage");
//        loginByHash.put("29ebd1d3c65dabcc8b406ca194b676fe", "5.4_stage");
//        loginByHash.put("e10adc3949ba59abbe56e057f20f883e", "a.v.petrov");
//        loginByHash.put("d8578edf8458ce06fbc5bb76a58c5ca4", "v.v.belov");
//        loginByHash.put("482c811da5d5b4bc6d497ffa98491e38", "a.s.ivanov");
//        loginByHash.put("5f4dcc3b5aa765d61d8327deb882cf99", "k.p.maslov");
        return loginByHash;
    }

    /*
    4 знака: 28106 мс
     */
    @Test
    @DisplayName("Брутфорсим в одном потоке")
    void bruteForceOneThread() {
        ConcurrentMap<String, String> passwordByLogin = new ConcurrentHashMap<>();
        ConcurrentMap<String, String> loginByHash = getLoginByHashConcurrentMap();

        long start = System.currentTimeMillis();

        for (long i = 0; i < Math.pow(62, 6) && !loginByHash.isEmpty(); i++) {

            for (int j = 1; j <= 6 && !loginByHash.isEmpty(); j++) {
                String password = PasswordUtils.getPasswordByItsNumber(i, j);
                String hash = PasswordUtils.encodeToMD5(password);
                String login = loginByHash.remove(hash);
                if (login == null) {
                    continue;
                }
                System.out.printf("p: %s, h: %s, l: %s\t%d%n", password, hash, login, System.currentTimeMillis() - start);
                passwordByLogin.put(login, password);
            }
        }

        System.out.println(passwordByLogin);
    }

    /*
    4 знака: 9664 мс
    5 знаков: 578889 мс
     */
    @Test
    @DisplayName("Брутфорсим в 12-ти потоках")
    void bruteForce12Thread() throws Exception {
        ConcurrentMap<String, String> passwordByLogin = new ConcurrentHashMap<>();
        ConcurrentMap<String, String> loginByHash = getLoginByHashConcurrentMap();

        long start = System.currentTimeMillis();
        try (ThreadPool pool = FixedThreadPool.create(12)) {
            pool.start();
            for (long i = 0; i < Math.pow(62, 6) / 2 && !loginByHash.isEmpty(); i++) {
                final long passNum = i;
                pool.execute(() -> {
                    for (int j = 1; j <= 6 && !loginByHash.isEmpty(); j++) {
                        String password = PasswordUtils.getPasswordByItsNumber(passNum, j);
                        String hash = PasswordUtils.encodeToMD5(password);
                        String login = loginByHash.remove(hash);
                        if (login == null) {
                            continue;
                        }
                        System.out.printf(
                            "p: %s, h: %s, l: %s\t%d%n",
                            password,
                            hash,
                            login,
                            System.currentTimeMillis() - start
                        );
                        System.out.println(pool.queueSize());
                        passwordByLogin.put(login, password);
                    }
                });
            }
            while (!loginByHash.isEmpty()){
                // wait for pool queue finish
            }
        }


        System.out.println(passwordByLogin);
        System.out.println(System.currentTimeMillis() - start);
    }
}
