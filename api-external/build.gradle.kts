description = "api-external module"

dependencies {
}

tasks.named<Jar>("bootJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}