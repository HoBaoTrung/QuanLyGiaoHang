package com.hbt.QuanLyGiaoHangBackend.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // Đường dẫn đến tệp khóa JSON của bạn
//        FileInputStream serviceAccount =
//                new FileInputStream("src/main/resources/static/serviceAccountKey.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/static/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://quan-ly-giao-hang-project-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();

        FirebaseApp.initializeApp(options);

        // Khởi tạo FirebaseApp nếu chưa được khởi tạo
        if (FirebaseApp.getApps().isEmpty()) {

            return FirebaseApp.initializeApp(options);
        } else {

            return FirebaseApp.getInstance();
        }
    }


}
