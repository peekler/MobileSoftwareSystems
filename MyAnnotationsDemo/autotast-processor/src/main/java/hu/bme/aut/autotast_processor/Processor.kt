package hu.bme.aut.autotast_processor

import com.squareup.kotlinpoet.FileSpec
import hu.bme.aut.autotoast_annotations.AutoToast
import hu.bme.aut.autotoast_annotations.ShowToast
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class Processor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes() =
        mutableSetOf(AutoToast::class.java.canonicalName)

    override fun process(annotations: MutableSet<out TypeElement>?,
                         roundEnv: RoundEnvironment
    ): Boolean {
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
            ?: return false

        roundEnv.getElementsAnnotatedWith(AutoToast::class.java)
            .forEach {
                val modelData = getModelData(it)
                val fileName = "${modelData.className}Toaster"
                FileSpec.builder(modelData.packageName, fileName)
                    .addType(ToastCodeBuilder(fileName, modelData).build())
                    .build()
                    .writeTo(File(kaptKotlinGeneratedDir))
            }

        return true
    }

    private fun getModelData(elem: Element): ModelData {
        val packageName = processingEnv.elementUtils.getPackageOf(elem).toString()
        val className = elem.simpleName.toString()
        val stringValues = mutableListOf<String>()

        elem.enclosedElements.forEach {
            System.out.println("${it.simpleName.toString()}")

            if (it.getAnnotation(ShowToast::class.java) != null) {
                val elementName = it.simpleName.toString()
                System.out.println("value ${it.toString()}")
                //stringValues.add(elementName.substring(0, elementName.indexOf('$')))
                stringValues.add(elementName.substring(3, elementName.indexOf('$')).toLowerCase())
            }

        }
        return  ModelData(packageName, className, stringValues)
    }


    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}

data class ModelData(
    val packageName: String,
    val className: String,
    val stringValues: List<String>
)