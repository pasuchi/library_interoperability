package com.bcp.presenter.banklist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bcp.presenter.R
import com.bcp.presenter.util.ShimmerList

@Composable
fun BanksScreen(
    viewModel: BankListViewModel = hiltViewModel()
) {
    val uiState: BanksUiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        BanksUiState.ShowShimmer -> Shimmer()
        is BanksUiState.RenderBanks -> RenderBanks(banks = (uiState as BanksUiState.RenderBanks).data)
        is BanksUiState.Error -> Unit
    }

    viewModel.processIntent(BanksIntent.LoadInit(phoneNumber = ""))
}



@Composable
private fun Shimmer() {
    Column(
        modifier = Modifier.padding(
            start = 24.dp,
            end = 24.dp,
            top = 22.dp,
            bottom = 22.dp
        )
    ) {
        ShimmerList()
        Text(
            text = "Ver más entidades", modifier = Modifier.padding(
                top = 15.dp,
                bottom = 15.dp
            )
        )
        ShimmerList()
    }
}

@Composable
private fun RenderBanks(banks: List<BankModel>) {
    Column(
        modifier = Modifier.padding(
            start = 24.dp,
            end = 24.dp,
            top = 22.dp,
            bottom = 22.dp
        )
    ) {
        LazyColumn(content = {
            items(items = banks) {
                ItemOwnProducts(name = it.name)
            }
        })
        Text(
            text = "Ver más entidades", modifier = Modifier.padding(
                top = 15.dp,
                bottom = 15.dp
            )
        )
    }
}


/*Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 22.dp, bottom = 22.dp)) {

    ShimmerList(isLoading = true, contentAfterLoading = {
        LazyColumn(content = {
            items(items = emptyList<String>()) {
                ItemOwnProducts()
            }
        })
    })

    Text(
        text = "Ver más entidades", modifier = Modifier.padding(
            top = 15.dp,
            bottom = 15.dp
        )
    )
    ShimmerList(isLoading = true, contentAfterLoading = {
        LazyColumn(content = {

            items(items = emptyList<String>()) {
                ItemOtherBanks()
            }

        })
    })
}*/


@Composable
fun ItemOwnProducts(name: String) {
    Column(modifier = Modifier.padding(top = 5.dp, bottom = 10.dp, start = 15.dp)) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_yape_logo_in),
                contentDescription = "",
                Modifier.clip(shape = RoundedCornerShape(20.dp))
            )
            Text(text = name, modifier = Modifier.padding(start = 15.dp))
        }
        Divider(thickness = 1.dp, color = Color.Black)
    }
}

@Composable
fun ItemOtherBanks(name: String) {
    Column(modifier = Modifier.padding(top = 5.dp, bottom = 10.dp, start = 15.dp)) {
        Row {
            Text(text = name)
        }
        Divider(thickness = 1.dp, color = Color.Black)
    }
}