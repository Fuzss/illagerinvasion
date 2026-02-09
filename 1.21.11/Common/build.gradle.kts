plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-common")
}

dependencies {
    modCompileOnlyApi(libs.puzzleslib.common)
    modCompileOnlyApi(libs.neoforgedatapackextensions.common)
}

multiloader {
    mixins {
        mixin("IllusionerMixin", "PatrolSpawnerMixin", "TemplateStructurePieceMixin", "WoodlandMansionPieceMixin")
    }
}
