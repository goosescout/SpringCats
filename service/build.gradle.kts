plugins {
    id("java")
}

group = "com.goosescout.spring"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":dao"))

    implementation("org.springframework.data:spring-data-jpa:3.0.5")
    implementation("org.springframework:spring-context:5.3.19")
    implementation("org.springframework:spring-beans:6.0.7")
    implementation("org.springframework.security:spring-security-crypto:6.0.3")
    implementation("org.springframework.security:spring-security-core:6.0.3")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testCompileOnly("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
}

tasks.test {
    useJUnitPlatform()
}