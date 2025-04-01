package com.example.assaply.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assaply.R
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.util.Dimensions.ArticleCardSize
import com.example.assaply.util.Dimensions.ExtraSmallPadding
import com.example.assaply.util.Dimensions.ExtraSmallPadding2
import com.example.assaply.util.Dimensions.SmallIconSize
import com.example.assaply.ui.theme.AssaplyTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val isInPreview = LocalInspectionMode.current

    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(ExtraSmallPadding)
    ) {
        val imageModifier = Modifier
            .size(ArticleCardSize)
            .clip(MaterialTheme.shapes.medium)

        if (isInPreview) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_report_image),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(ExtraSmallPadding))

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .height(ArticleCardSize)
                .weight(1f)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Text(
                    text = formatPublishedAt(article.publishedAt),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    AssaplyTheme {
        ArticleCard(
            modifier = Modifier,
            article = Article(
                author = "",
                content = "",
                description = "",
                publishedAt = "4am",
                source = Source(id = "", name = "BBC"),
                title = "Woman crashed into bicycle on full speed and demolished the owner",
                url = "",
                urlToImage = ""
            ),
            onClick = {}
        )
    }
}

