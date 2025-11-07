# Getting Started

## How to Start Application

1. At the root directory run the below command to Postgres DB

    ```bash
    docker compose up -d
    ```
   
2. Run the Application
3. Launch [Swagger UI](http://localhost:8080/swagger-ui/index.html).
4. Register
5. Login
6. Get the Token and Validate it on [jwt.io](https://www.jwt.io/)

## Commands to Generate Private and Public Keys
    
1. Command to create Private Key PEM File
    ```bash
    openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
    ```

2. Command to create Public Key PEM File from Private Key
    ```bash
    openssl rsa -pubout -in private_key.pem -out public_key.pem
    ```

## Reference Links

1. GitHub Link: [GitHub Project](https://github.com/ali-bouali/spring-security-asymmetric-encryption)
2. YouTube Link: [Ali Bouali - Spring Security JWT - Asymmetric Encryption](https://youtu.be/bLA7avkOpIQ?si=DB__oZSbTMIyeDqQ)
