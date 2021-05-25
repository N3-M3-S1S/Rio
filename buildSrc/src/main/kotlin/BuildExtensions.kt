import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun DependencyHandlerScope.implementation(dependencyNotation: Any) {
    "implementation"(dependencyNotation)
}

internal fun DependencyHandlerScope.testImplementation(dependencyNotation: Any) {
    "testImplementation"(dependencyNotation)
}

internal fun DependencyHandlerScope.androidTestImplementation(dependencyNotation: Any) {
    "androidTestImplementation"(dependencyNotation)
}

internal fun Project.android(configure: BaseExtension.() -> Unit) {
    android.configure()
}

private val Project.android: BaseExtension
    get() = extensions.getByType()

val BaseExtension.kotlinOptions: KotlinJvmOptions
    get() = (this as ExtensionAware).extensions.getByType()
