package com.loongwind.frp.client.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.loongwind.ardf.base.BaseBindingActivity
import com.loongwind.frp.client.R
import com.loongwind.frp.client.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun initDataBinding(binding: ActivityMainBinding) {
        binding.content.bottomNavigation.setupWithNavController(findNavController(R.id.nav_host_fragment_content_main))
    }
}