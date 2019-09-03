plugins {
    kotlin("jvm") version "1.3.31"

    `build-scan`
    `maven-publish`
    signing
    idea
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
//    implementation(platform("com.fasterxml.jackson:jackson-bom:2.9.9"))
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.9.9")
    implementation("com.fasterxml.jackson.core:jackson-core:2.9.9")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.9")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.9")


    testImplementation(platform("org.junit:junit-bom:5.5.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}
val sonatypeUsername = System.getenv("SONATYPE_USERNAME")
val sonatypePassword = System.getenv("SONATYPE_PASSWORD")

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                name.set("Four Leaf Clover")
                description.set("Clover report parser")
                url.set("https://github.com/alde/fourleafclover")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("alde")
                        name.set("Rickard Dybeck")
                        email.set("r.dybeck@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/alde/fourleafclover.git")
                    developerConnection.set("scm:git:ssh://github.com/alde/fourleafclover.git")
                    url.set("https://github.com/alde/fourleafclover")
                }
            }
            groupId = "nu.alde"
            artifactId = "fourleafclover"
            version = "0.0.1"

            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())
        }
    }
    repositories {
        maven {
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}


buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    publishAlways()
}
