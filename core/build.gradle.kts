import net.labymod.labygradle.common.extension.LabyModAnnotationProcessorExtension.ReferenceType

dependencies {
    labyProcessor()
    api(project(":api"))
    implementation("com.google.guava:guava:33.4.8-jre")
}

labyModAnnotationProcessor {
    referenceType = ReferenceType.DEFAULT
}