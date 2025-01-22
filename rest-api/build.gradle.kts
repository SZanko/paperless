import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	java
    jacoco
	id("org.springframework.boot") version "3.4.1"
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

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}




val jakartaServlet = "6.1.0"
val jakartaCdi= "4.1.0"
val jacksonDatabind = "0.2.6"
val springdoc = "2.6.0"
val jakartaInterceptor = "2.2.0"
val mapstruct = "1.6.0"
val minio = "8.5.12"

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
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdoc")
	implementation("org.openapitools:jackson-databind-nullable:$jacksonDatabind")
	implementation("jakarta.interceptor:jakarta.interceptor-api:$jakartaInterceptor")
	implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:$jakartaCdi")
	compileOnly("jakarta.servlet:jakarta.servlet-api:$jakartaServlet")
	implementation("org.mapstruct:mapstruct:$mapstruct")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstruct")
	implementation("io.minio:minio:$minio")
}

hibernate {
	enhancement {
		enableAssociationManagement = true
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
repositories {
	mavenCentral()
	mavenLocal()
}
