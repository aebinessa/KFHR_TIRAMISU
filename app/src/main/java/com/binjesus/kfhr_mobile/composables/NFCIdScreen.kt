import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Employee

@Composable
fun NFCIdScreen(navController: NavHostController, employee: Employee, nfcViewModel: NfcViewModel = viewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity

    var isNfcSupported by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            isNfcSupported = false
        } else {
            nfcViewModel.initializeNfc(nfcAdapter, context)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            activity?.let {
                nfcViewModel.disableNfcForegroundDispatch(it as AppCompatActivity)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF4CAF50))
                )

                // Back button
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                // Notification button
                IconButton(
                    onClick = { /* Handle notification click */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = employee.id.toString(),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = rememberImagePainter(
                            data = employee.profilePicURL.ifEmpty { "https://example.com/profile.jpg" }, // Hardcoded URL for testing
                            builder = {
                                placeholder(R.drawable.user)
                                error(R.drawable.warning)
                            }
                        ),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(120.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = employee.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Group Human Resources",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.nfc),
                contentDescription = "Scan Icon",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Image(
                painter = painterResource(id = R.drawable.kfh),
                contentDescription = "KFH Logo",
                modifier = Modifier
                    .size(300.dp)  // Increased size of the KFH Logo image
                    .align(Alignment.CenterHorizontally)
            )
        }

        // Ensure NFC foreground dispatch is enabled when the screen is active
        LaunchedEffect(Unit) {
            activity?.let {
                nfcViewModel.enableNfcForegroundDispatch(it as AppCompatActivity)
            }
        }
    }
}

