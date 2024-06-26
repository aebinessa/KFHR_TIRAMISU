package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Certificate
import com.binjesus.kfhr_mobile.ui.theme.DarkGreen
import com.binjesus.kfhr_mobile.ui.theme.LightGreen
import com.binjesus.kfhr_mobile.utils.Route

import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun MyCertificates(navController: NavHostController, viewModel: KFHRViewModel = viewModel()) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightGreen)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "My Certificates",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            end = 16.dp
                        )
                )
                IconButton(onClick = { navController.navigate(Route.NotificationsRoute) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Notifications",
                        tint = Color.Black,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.SubmitCertificateRoute) },
                backgroundColor = DarkGreen,
                modifier = Modifier
                    .padding(
                        bottom = 24.dp,
                        end = 16.dp
                    )
                    .size(75.dp)
                    .shadow(elevation = 20.dp, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()
            .background(LightGreen)) {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(viewModel.MyCertificates) { certificate ->
                    CertificateCard(certificate = certificate)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Button(
                onClick = { navController.navigate(Route.RecommendedCertificatesRoute) },
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        bottom = 24.dp,
                        start = 16.dp
                    )
                    .size(150.dp, 50.dp)
            ) {
                Text("Recommended", color = Color.White)
            }
        }
    }
}

@Composable
fun CertificateCard(certificate: Certificate) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle card click */ }
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = DarkGreen
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = certificate.certificateName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Issue Date: ${certificate.issueDate}",
                fontSize = 14.sp,
                color = Color.White
            )
            Text(
                text = "Expiration Date: ${certificate.expirationDate}",
                fontSize = 14.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = certificate.verificationURL,
                fontSize = 14.sp,
                color = LightGreen,
                modifier = Modifier.clickable { /* Handle URL click */ }
            )
        }
    }
}


