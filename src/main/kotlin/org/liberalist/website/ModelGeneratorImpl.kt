package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import org.liberalist.website.json.JsonUtil
import org.liberalist.website.tree.Branch
import org.liberalist.website.tree.Tree
import java.nio.charset.Charset
import java.nio.file.Path

class ModelGeneratorImpl(
        private val generatedDirectory: Path,
        private val files: FilesContract,
        private val charset: Charset,
        private val modelFactory: ModelFactory) : ModelGenerator {
    override fun generateModel(htmlGeneratorResult: Tree<HtmlConversion>) {
        val tree: Tree<String> = htmlGeneratorResult.map { it.name }
        tree as Branch<String>
        val titles = createTitlesMap(htmlGeneratorResult)
        val model = modelFactory.createModel(tree, titles)
        val modelJson = JsonUtil.mapper.writeValueAsString(model)
        val modelPath = generatedDirectory.resolve("model.json")
        files.writeString(modelPath, modelJson, charset)
    }

    private fun createTitlesMap(htmlGeneratorResult: Tree<HtmlConversion>): Map<String, String> =
            htmlGeneratorResult.leafNodes().map { leaf ->
                val htmlConversion = leaf.value as HtmlConversionFile
                Pair(htmlConversion.name, htmlConversion.title)
            }.toMap()
}
