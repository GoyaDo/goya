# postgresql 创建 nacos 数据库 脚本 

```
-- postgresql 创建 nacos 数据库 脚本


-- 创建用户 nacos 密码是 nacos
CREATE USER security_demo WITH PASSWORD 'goya';

-- 创建数据库 nacos, 并将用户 nacos 分配给该数据
CREATE DATABASE security_demo OWNER security_demo;

-- 将数据库 nacos 的所有权限分配给 nacos
GRANT ALL PRIVILEGES ON DATABASE security_demo TO security_demo;

```