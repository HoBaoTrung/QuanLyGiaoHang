# Sử dụng hình ảnh chính thức của OpenJDK 21
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

COPY src/main/resources/static /app/src/main/resources/static

# Copy file JAR từ máy chủ vào container
COPY target/QuanLyGiaoHangBackend-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
