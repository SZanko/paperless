import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.hibernate.orm") version "6.5.3.Final"
	id("io.freefair.lombok") version "8.10.2"
	//id("org.graalvm.buildtools.native") version "0.10.3"
}

group = "at.fhtw.swkom"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

tasks.named<BootBuildImage>("bootBuildImage") {
	imageName.set("fhtw.at/swkom/${project.name}")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	//implementation("org.liquibase:liquibase-core")
	implementation("org.springframework.amqp:spring-rabbit-stream")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("jakarta.interceptor:jakarta.interceptor-api:2.2.0")
	implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.1.0")
	compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

}

hibernate {
	enhancement {
		enableAssociationManagement = true
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
