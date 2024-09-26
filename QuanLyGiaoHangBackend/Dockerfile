# Sử dụng hình ảnh chính thức của OpenJDK 21
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ máy chủ vào container
COPY target/quan-ly-giao-hang.jar app.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
