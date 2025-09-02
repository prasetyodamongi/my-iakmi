package com.pcoding.myiakmi.ui.main.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.data.model.Event
import com.pcoding.myiakmi.ui.components.cards.ErrorCard
import com.pcoding.myiakmi.ui.components.cards.EventCard
import com.pcoding.myiakmi.ui.components.cards.LoadingCard
import com.pcoding.myiakmi.ui.components.cards.TopMenuCard
import com.pcoding.myiakmi.ui.main.activity.DetailEventActivity
import com.pcoding.myiakmi.ui.theme.MyIAKMITheme
import com.pcoding.myiakmi.utils.SessionManager
import com.pcoding.myiakmi.viewmodel.EventViewModel
import kotlinx.coroutines.launch

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = viewModel()
) {
    val context = LocalContext.current
    val userData = SessionManager.getUserData(context)
    val dataEvent by viewModel.dataEvent.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val webView = remember { WebView(context) }
    var isWebViewLoading by remember { mutableStateOf(true) }
    var isWebViewError by remember { mutableStateOf(false) }
    val transition = rememberInfiniteTransition(label = "")
    val shimmerOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            colorResource(R.color.unselected_color).copy(alpha = 0.6f),
            colorResource(R.color.unselected_color).copy(alpha = 0.3f),
            colorResource(R.color.unselected_color).copy(alpha = 0.6f)
        ),
        start = Offset(shimmerOffset, shimmerOffset / 3),
        end = Offset(shimmerOffset * 2, shimmerOffset)
    )

    LaunchedEffect(Unit) {
        viewModel.reqEvent(
            userData.keanggotaan,
            userData.email,
            userData.userid,
            userData.lengkap,
            userData.isadmin,
            userData.islogin,
            userData.notactiveyet,
            userData.member
        )
        viewModel.resetError()
    }

    MyIAKMITheme(statusBarColor = colorResource(R.color.white), darkTheme = true) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.title_topbar_home),
                    modifier = Modifier.align(Alignment.Center),
                    color = colorResource(R.color.main_color),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                TopMenu()
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.event),
                    modifier = modifier.padding(start = 16.dp),
                    color = colorResource(R.color.main_color),
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontSize = 18.sp
                )
                Text(
                    text = stringResource(R.string.subtitle_event),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = colorResource(R.color.text_color1),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_italic)),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(2.dp))
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        LoadingCard()
                    } else if (isError.isNotEmpty()) {
                        ErrorCard(
                            onRetry = {
                                viewModel.reqEvent(
                                    userData.keanggotaan,
                                    userData.email,
                                    userData.userid,
                                    userData.lengkap,
                                    userData.isadmin,
                                    userData.islogin,
                                    userData.notactiveyet,
                                    userData.member
                                )
                                viewModel.resetError()
                            }
                        )
                    } else {
                        SwipeableFullWidthCard(
                            events = dataEvent,
                            modifier = Modifier.wrapContentHeight()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.news),
                        modifier = modifier
                            .align(Alignment.CenterStart),
                        color = colorResource(R.color.main_color),
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontSize = 18.sp
                    )
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterEnd)
                            .alpha(if (isWebViewError) 1f else 0f)
                            .clickable {
                                isWebViewLoading = true
                                isWebViewError = false
                                webView.reload()
                            },
                        tint = colorResource(R.color.selected_color)
                    )
                }
                Spacer(modifier = modifier.height(2.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = if (isWebViewLoading) CardDefaults.cardColors(colorResource(R.color.selected_color)) else CardDefaults.cardColors(
                        colorResource(R.color.main_color)
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    if (isWebViewLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                                .background(shimmerBrush)
                        )
                    }
                    AndroidView(
                        factory = {
                            webView.apply {
                                settings.javaScriptEnabled = true
                                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                                webViewClient = object : WebViewClient() {
                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        isWebViewLoading = false
                                    }

                                    override fun onReceivedError(
                                        view: WebView?,
                                        request: WebResourceRequest?,
                                        error: WebResourceError?
                                    ) {
                                        isWebViewLoading = false
                                        isWebViewError = true
                                    }
                                }
                                loadUrl("https://iakmi.or.id/web/news")
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .alpha(if (isWebViewLoading) 0f else 1f)
                    )
                }
            }
        }
    }
}

@Composable
fun SwipeableFullWidthCard(
    events: List<Event>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { events.size }
    )
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            EventCard(event = events[page]) {
                val intent = Intent(context, DetailEventActivity::class.java).apply {
                    putExtra("event_id", events[page].evid)
                    putExtra("event_name", events[page].name)
                    putExtra("event_location", events[page].location)
                    putExtra("event_time", events[page].time)
                }
                context.startActivity(intent)
            }
        }

        // Left Arrow
        if (pagerState.currentPage > 0) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 16.dp)
                    .background(Color.White.copy(alpha = 0.6f), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back_ios),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        // Right Arrow
        if (pagerState.currentPage < events.size - 1) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 16.dp)
                    .background(Color.White.copy(alpha = 0.6f), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_forward_ios),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun TopMenu() {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TopMenuCard(
            stringResource(R.string.lpp_iakmi),
            painterResource(R.drawable.ic_pendidikan),
            20.dp
        ) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LLP_IAKMI_URL))
            context.startActivity(intent)
        }
        TopMenuCard(stringResource(R.string.journal), painterResource(R.drawable.ic_journal)) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(JURNAL_URL))
            context.startActivity(intent)
        }
        TopMenuCard(
            stringResource(R.string.mentoring),
            painterResource(R.drawable.ic_pelatihan),
            20.dp
        ) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PELATIHAN_URL))
            context.startActivity(intent)
        }
        TopMenuCard(stringResource(R.string.pengda_login), painterResource(R.drawable.ic_login)) {
            Toast.makeText(context, "Fitur belum tersedia!", Toast.LENGTH_SHORT).show()
        }
    }
}

const val LLP_IAKMI_URL = "https://lppiakmi.or.id/"
const val JURNAL_URL = "https://journal.iakmi.or.id/"
const val PELATIHAN_URL = "https://lppiakmi.or.id/kalender-pelatihan"

private fun loadWebViewContent(
    onWebViewLoading: (Boolean) -> Unit,
    onWebViewError: (Boolean) -> Unit
) {
    onWebViewLoading(true) // Set loading to true
    onWebViewError(false) // Clear any previous error
    // Anda bisa menambahkan logika untuk memuat ulang URL WebView jika diperlukan.
}

// Kemudian panggil fungsi ini dari bagian onRetry pada ErrorWebViewCard


