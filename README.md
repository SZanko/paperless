# FH Technikum SWKOM WS2025/26

 This project is trying to implement a paperless ocr document scanning system, for more information see semester-project.pdf


# Before Start

run mvn spring-boot:build-image for the restapi in the rest-api directory
run npm run build in the frontend directory
run mvn install in workers
Generate minio secretkey and accesskey and set it in docker-compose

# After Start
ElasticSearch Index is created by posting a document
