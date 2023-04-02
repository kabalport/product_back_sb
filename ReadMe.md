



# sql 설정 - 사용자 계정 설정
```mariadb
create database web;

CREATE USER 'user'@'%' IDENTIFIED BY '1234';

CREATE USER 'user'@'localhost' IDENTIFIED BY '1234';

FLUSH PRIVILEGES;

GRANT ALL PRIVILEGES ON web.* TO 'user'@'%';

GRANT ALL PRIVILEGES ON web.* TO 'user'@'localhost';
```

# sql 설정 - 테이블 생성 및 레코드 입력
```mariadb
USE web;

CREATE TABLE product(
product_code INT AUTO_INCREMENT PRIMARY KEY,
product_name VARCHAR(100) NOT NULL,
description VARCHAR(5000),
price INT DEFAULT 0,
filename VARCHAR(500)
);


INSERT INTO product (product_name, description, price) VALUES ('사과', '맛있는 사과입니다.', 500);


SELECT * FROM product;


```