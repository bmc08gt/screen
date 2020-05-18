package dev.bmcreations.screen

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dev.bmcreations.scrcast.app.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        with(findNavController(R.id.nav_host_fragment)) {
            navigate(Uri.parse("screen.app://library"))
        }
    }
}
