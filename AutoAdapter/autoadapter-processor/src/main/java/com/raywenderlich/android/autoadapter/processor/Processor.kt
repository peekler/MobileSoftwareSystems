/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.android.autoadapter.processor

import com.raywenderlich.android.autoadapter.annotations.AdapterModel
import com.raywenderlich.android.autoadapter.annotations.ViewHolderBinding
import com.raywenderlich.android.autoadapter.processor.codegen.AdapterCodeBuilder
import com.raywenderlich.android.autoadapter.processor.models.ModelData
import com.raywenderlich.android.autoadapter.processor.models.ViewHolderBindingData
import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class Processor : AbstractProcessor() {

  override fun getSupportedAnnotationTypes() = mutableSetOf(
          AdapterModel::class.java.canonicalName)

  override fun process(annotations: MutableSet<out TypeElement>?,
      roundEnv: RoundEnvironment): Boolean {

    val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        ?: return false

    roundEnv.getElementsAnnotatedWith(AdapterModel::class.java)
        .forEach {
          val modelData = getModelData(it)
          val fileName = "${modelData.modelName}Adapter"
          FileSpec.builder(modelData.packageName, fileName)
              .addType(AdapterCodeBuilder(fileName, modelData).build())
              .build()
              .writeTo(File(kaptKotlinGeneratedDir))
        }
    return true
  }

  private fun getModelData(elem: Element): ModelData {
    val packageName = processingEnv.elementUtils.getPackageOf(elem).toString()
    val modelName = elem.simpleName.toString()
    val annotation = elem.getAnnotation(AdapterModel::class.java)
    val layoutId = annotation.layoutId
    val viewHolderBindingData = elem.enclosedElements.mapNotNull {
      val viewHolderBinding = it.getAnnotation(ViewHolderBinding::class.java)
      if (viewHolderBinding == null) {
        null
      } else {
        val elementName = it.simpleName.toString()
        val fieldName = elementName.substring(0, elementName.indexOf('$'))
        ViewHolderBindingData(fieldName, viewHolderBinding.viewId)
      }
    }
    return ModelData(
        packageName, modelName, layoutId, viewHolderBindingData)
  }

  companion object {
    const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
  }
}