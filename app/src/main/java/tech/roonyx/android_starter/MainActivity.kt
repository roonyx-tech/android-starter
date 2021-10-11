package tech.roonyx.android_starter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.roonyx.android_starter.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(), MainFragment.TAG)
                .commitNow()
        }
    }
}