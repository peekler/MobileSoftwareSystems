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

package com.raywenderlich.android.autoadapter.processor.codegen

import com.raywenderlich.android.autoadapter.processor.models.ModelData
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class AdapterCodeBuilder(private val adapterName: String, private val data: ModelData) {

  private val viewHolderName = "ViewHolder"
  private val viewHolderClassName = ClassName(data.packageName, viewHolderName)
  private val viewHolderQualifiedClassName = ClassName(data.packageName, "$adapterName.$viewHolderName")
  private val modelClassName = ClassName(data.packageName, data.modelName)
  private val itemsListClassName = ClassName("kotlin.collections", "List")
      .parameterizedBy(modelClassName)
  private val textViewClassName = ClassName("android.widget", "TextView")

  fun build(): TypeSpec = TypeSpec.classBuilder(adapterName)
      .primaryConstructor(FunSpec.constructorBuilder()
          .addParameter("items", itemsListClassName)
          .build()
      )
      .superclass(ClassName("androidx.recyclerview.widget.RecyclerView", "Adapter")
          .parameterizedBy(viewHolderQualifiedClassName)
      )
      .addProperty(PropertySpec.builder("items", itemsListClassName)
          .addModifiers(KModifier.PRIVATE)
          .initializer("items")
          .build()
      )
      .addBaseMethods()
      .addViewHolderType()
      .build()

  private fun TypeSpec.Builder.addBaseMethods(): TypeSpec.Builder = apply {
    addFunction(FunSpec.builder("getItemCount")
        .addModifiers(KModifier.OVERRIDE)
        .returns(INT)
        .addStatement("return items.size")
        .build()
    )

    addFunction(FunSpec.builder("onCreateViewHolder")
        .addModifiers(KModifier.OVERRIDE)
        .addParameter("parent", ClassName("android.view", "ViewGroup"))
        .addParameter("viewType", INT)
        .returns(viewHolderQualifiedClassName)
        .addStatement("val view = android.view.LayoutInflater.from(parent.context).inflate(%L, " +
            "parent, false)", data.layoutId)
        .addStatement("return $viewHolderName(view)")
        .build()
    )

    addFunction(FunSpec.builder("onBindViewHolder")
        .addModifiers(KModifier.OVERRIDE)
        .addParameter("viewHolder", viewHolderQualifiedClassName)
        .addParameter("position", INT)
        .addStatement("viewHolder.bind(items[position])")
        .build()
    )
  }

  private fun TypeSpec.Builder.addViewHolderType(): TypeSpec.Builder = addType(
      TypeSpec.classBuilder(viewHolderClassName)
          .primaryConstructor(FunSpec.constructorBuilder()
              .addParameter("itemView", ClassName("android.view", "View"))
              .build()
          )
          .superclass(ClassName("androidx.recyclerview.widget.RecyclerView", "ViewHolder"))
          .addSuperclassConstructorParameter("itemView")
          .addBindingMethod()
          .build()
  )

  private fun TypeSpec.Builder.addBindingMethod(): TypeSpec.Builder = addFunction(FunSpec.builder("bind")
      .addParameter("item", modelClassName)
      .apply {
        data.viewHolderBindingData.forEach {
          addStatement("itemView.findViewById<%T>(%L).text = item.%L",
              textViewClassName, it.viewId, it.paramName)
        }
      }
      .build()
  )
}
