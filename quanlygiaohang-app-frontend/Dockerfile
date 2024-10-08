# Sử dụng Node.js làm base image
FROM node:20

# Tạo thư mục làm việc cho ứng dụng
WORKDIR /app

# Sao chép file package.json và package-lock.json để cài đặt dependencies
COPY package*.json ./

# Cài đặt dependencies
RUN npm install

# Sao chép toàn bộ mã nguồn của ứng dụng vào container
COPY . .

# Build ứng dụng ReactJS
RUN npm run build

# Sử dụng nginx để serve ứng dụng React đã build
FROM nginx:alpine
COPY --from=0 /app/build /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Khởi động Nginx server
CMD ["nginx", "-g", "daemon off;"]
