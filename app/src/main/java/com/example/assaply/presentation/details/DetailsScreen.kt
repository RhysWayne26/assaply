package com.example.assaply.presentation.details

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assaply.R
import com.example.assaply.data.domain.model.Article
import com.example.assaply.data.domain.model.Source
import com.example.assaply.presentation.Dimensions.ArticleImageHeight
import com.example.assaply.presentation.Dimensions.MediumPadding1
import com.example.assaply.presentation.details.components.DetailsTopBar
import com.example.assaply.ui.theme.AssaplyTheme

@SuppressLint("QueryPermissionsNeeded")
@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent)->Unit,
    navigateUp:()->Unit
){
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ){
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also{
                    it.data = article.url.toUri()
                    if (it.resolveActivity(context.packageManager)!=null){
                       context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also{
                    it.putExtra(Intent.EXTRA_TEXT,article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager)!=null){
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = {
                event(DetailsEvent.UpsertDeleteArticle(article))
            },
            onBackClick = navigateUp
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MediumPadding1,
                end = MediumPadding1,
                top = MediumPadding1
            )
        ){
            item{
                AsyncImage(
                    model = ImageRequest.Builder(context = context).data(article.urlToImage).build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(ArticleImageHeight).clip(MaterialTheme.shapes.medium)
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(text = article.title, style = MaterialTheme.typography.displaySmall, color = colorResource(
                    id = R.color.text_title
                ))

                Text(text = article.content, style = MaterialTheme.typography.bodyMedium, color = colorResource(
                    id = R.color.body
                ))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    val mockArticle = Article(
        author = "John Doe",
        content = "Here is some preview content for the article.",
        description = "This is a sample article used for Compose preview.",
        publishedAt = "2025-03-29T12:00:00Z",
        source = Source(id = "1", name = "MockSource"),
        title = "Preview Article Title",
        url = "https://example.com",
        urlToImage = "https://example.com/image.jpg"
    )

    AssaplyTheme {
        DetailsScreen(
            article = mockArticle,
            event = {},
            navigateUp = {}
        )
    }
}