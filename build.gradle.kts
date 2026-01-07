import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.labotec.pe"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("io.netty:netty-all:4.1.88.Final")
    implementation("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // Dependencia principal de MapStruct
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus:1.11.2") // Micrometer para Prometheus

    implementation("com.fasterxml.jackson.core:jackson-databind")  // Para serializar/deserializar JSON
    // https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf
    implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("org.mapstruct:mapstruct:1.5.5.Final") // Usa la versión más reciente disponible
    // Dependencia para el procesador de MapStruct (para generación de código)
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("org.springframework.boot:spring-boot-starter-cache:3.3.4")
    implementation ("me.paulschwarz:spring-dotenv:3.0.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("mysql:mysql-connector-java:8.0.33") // Driver MySQL

}
tasks.named<BootJar>("bootJar") {
    archiveFileName.set("ms-tcp.jar")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
