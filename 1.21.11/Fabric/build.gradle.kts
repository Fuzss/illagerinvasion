plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-fabric")
}

dependencies {
    modApi(libs.fabricapi.fabric)
    modApi(libs.puzzleslib.fabric)
    modApi(libs.neoforgedatapackextensions.fabric)
    include(libs.neoforgedatapackextensions.fabric)
    modImplementation(libs.fabricasm.fabric)
    include(libs.fabricasm.fabric)
}

multiloader {
    modFile {
        json {
            entrypoint(
                "mm:early_risers",
                "${project.group}.${project.name.lowercase()}.asm.IllagerInvasionFabricCore"
            )
        }
    }
}
