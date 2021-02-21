package hu.bme.aut.autotast_processor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.sun.org.apache.xpath.internal.operations.Mod

class ToastCodeBuilder(
    private val toastBuilderClassName: String,
    private val data: ModelData
) {
    fun build(): TypeSpec = TypeSpec.classBuilder(toastBuilderClassName) // 1
        .primaryConstructor(
            FunSpec.constructorBuilder() // 2
                .build()
        )
        .addFunction(FunSpec.builder(
            "show${data.className}")
            .addParameter("context", ClassName("android.content", "Context"))
            .addParameter("data", ClassName(data.packageName, data.className))
            .addStatement("""
                android.widget.Toast.makeText(context, "${getStringValue(data)}".trim(), android.widget.Toast.LENGTH_LONG).show()
            """.trimIndent())
            .build()
        )
        .build()

    fun getStringValue(data: ModelData) : String {
        var result = ""
        data.stringValues.forEach {
            System.out.println(it)
            result = result.plus("\${data.$it}\\n")
        }
        return result
    }
}