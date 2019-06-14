package com.esi.dz_now.data

import java.util.*

class DataUtil {

    private lateinit var articlesList: MutableList<Article>

    init {
        creatArticleList()
    }

    fun getCategories(): List<Categories>{
        return Categories.values().toList()
    }

    fun getArticlesListByCategorie(categories: Categories): MutableList<Article>? {
        var list = getAllArticlesByCategories(articlesList)
        return list[categories]
    }

    fun getAllArticles(): MutableList<Article> {
        articlesList.sortByDescending { article ->
            article.date
        }
        return articlesList
    }


    private fun creatArticleList() {
        val articles = mutableListOf<Article>()
        /*var article1:Article = Article(
            0,
            "Ali Ghediri interpellé par les services de sécurité",
            "img",
            "",
        )*/


        for (categorie in Categories.values()) {
            for (i in 0..5) {
                articles.add(
                    Article(
                        i,
                        "Ali Ghediri interpellé par les services de sécurité",
                        "",
                      /*  "Le général à la retraite Ali Ghediri a été placé en détention par le tribunal de Dar El Beida, prés la cour d’Alger, devant lequel il a été déféré durant l’aprés midi de ce jeudi.\n" +
                                "Après une audition de plusieurs heures par le juge d’instruction, il a été placé sous mandat de dépôt en milieu de soirée. Ali Ghediri a été interpellé, hier, en fin de journée par des éléments des services de sécurité.\n" +
                                "Conduit dans une caserne de la direction de la sécurité du territoire, il a été interrogé durant une bonne partie de la nuit. Libéré à 3h du matin, avec une convocation pour se présenter ce jeudi au tribunal de Dar El Beida à 15h.  \n" +
                                "Selon nos informations, Ghediri est poursuivi, avec un certain Hacene Gouasmiya, pour falsification de signature lors de la collecte des signature pour la présidentielle annulée d’avril dernier.",
                        */
                        "أودع قاضي التحقيق بمحكمة الدار البيضاء، مساء الخميس، الجنرال المتقاعد علي غديري الحبس المؤقت بالحراش.\n" +
                                "وأكد بيان نشرته صفحة غديري، أنه “تم ايداع المترشح السابق للرئاسيات السيد علي غديري الحبس المؤقت، بعد مثوله أمام قاضي التحقيق لدى محكمة الدار البيضاء.\n" +
                                "وأوضح أنه “سيتم نشر بيان كاملا لاحقا” دون تقديم تفاصيل أكثر حول التهم التي يتابع فيها.",
                        categorie,
                        Date(),
                        true,
                        "El WATAN",
                        "Salima"
                    )
                )
            }
        }
        articlesList = articles
    }

    private fun getAllArticlesByCategories(ArticalesList: MutableList<Article>): MutableMap<Categories, MutableList<Article>> {

        val articlesListByCategories: MutableMap<Categories, MutableList<Article>> =
            ArticalesList.groupByTo(mutableMapOf()) { it.categories }
        return articlesListByCategories
    }

    fun getArticleById(articleId: Int): Article {
        var i = 0
        var article = articlesList[0]
        while (i < articlesList.size) {
            if (articlesList[i].id == articleId) {
                article = articlesList[i]
            }
            i++
        }
        return article
    }


}