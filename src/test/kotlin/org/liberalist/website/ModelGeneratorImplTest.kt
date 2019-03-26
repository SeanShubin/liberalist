package org.liberalist.website

import org.liberalist.website.json.JsonUtil
import org.liberalist.website.json.JsonUtil.normalizeJson
import org.liberalist.website.test.TestUtil
import org.liberalist.website.test.TestUtil.loadResource
import org.liberalist.website.tree.Branch
import org.liberalist.website.tree.Leaf
import org.liberalist.website.tree.Tree
import kotlin.test.Test

class ModelGeneratorImplTest {
    @Test
    fun sample() {
        // given
        val trees: List<Tree<String>> = listOf(
                Leaf("a"),
                Branch("b", listOf(
                        Leaf("c"),
                        Leaf("d"))),
                Leaf("e"))
        val titles = mapOf(
                Pair("a", "A Title"),
                Pair("c", "C Title"),
                Pair("d", "D Title"),
                Pair("e", "E Title"))
        val modelGenerator = ModelGeneratorImpl()
        val expectedJson = loadResource("/sample-model.json")

        // when
        val actual = modelGenerator.createModel(trees, titles)
        val actualJson = JsonUtil.mapper.writeValueAsString(actual)

        // then
        assertJsonEquals(expectedJson, actualJson)
    }

    private fun assertJsonEquals(expected: String, actual: String) {
        TestUtil.assertMultilineEquals(expected.normalizeJson(), actual.normalizeJson())
    }
}
