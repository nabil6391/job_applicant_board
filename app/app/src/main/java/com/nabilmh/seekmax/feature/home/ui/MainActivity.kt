package com.nabilmh.seekmax.feature.home.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nabilmh.seekmax.feature.auth.AuthViewModel
import com.nabilmh.seekmax.feature.auth.ui.LoginActivity
import com.nabilmh.seekmax.feature.home.MainViewModel
import com.nabilmh.seekmax.feature.home.model.Job
import com.nabilmh.seekmax.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import isScrollToEnd

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = mainViewModel.liveData.observeAsState()
            MyApplicationTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Jobs", color = MaterialTheme.colors.onPrimary) },
                            actions = {
                                IconButton(
                                    onClick = {
                                        authViewModel.logout()
                                        val intent = LoginActivity.newIntent(this@MainActivity)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ExitToApp,
                                        contentDescription = "logout",
                                        tint = MaterialTheme.colors.onPrimary
                                    )
                                }
                            },
                            elevation = AppBarDefaults.TopAppBarElevation
                        )
                    }
                ) {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                        HomeScreen(state.value, onLoadMore = {
                            mainViewModel.loadMore()
                        })
                    }
                }
            }
        }

        mainViewModel.getJobsList()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

@Composable
fun HomeScreen(state: MainViewModel.ViewState?, onLoadMore : () -> Unit) {
    if (state == null) {
        return
    }

    when (state) {
        is MainViewModel.ViewState.Error -> {

        }
        is MainViewModel.ViewState.Loaded -> {
            val listState = rememberLazyListState()

            val isScrollToEnd by remember { derivedStateOf { listState.isScrollToEnd() } }

            if (isScrollToEnd && !state.loadingMore) {
                onLoadMore()
            }
            LazyColumn(state = listState) {
                items(state.jobs.size) {
                    JobItemComposable(state.jobs[it])
                }

                if (state.loadingMore) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) { CircularProgressIndicator() }
                    }
                }

            }
        }
        MainViewModel.ViewState.Loading -> {

        }
    }

}

@Composable
private fun JobItemComposable(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .clip(RoundedCornerShape(6.dp))
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(
                text = job.positionTitle,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Company Name ${job.industry}", style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = job.description, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(10.dp))
            if (job.haveIApplied) {
                Row {
                    Text(text = "Applied")
                    Icon(Icons.Filled.CheckCircle, contentDescription = "Applied")
                }
            }
        }
    }
}
