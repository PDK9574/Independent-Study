/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.mediapipe.examples.gesturerecognizer

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.mediapipe.examples.gesturerecognizer.databinding.ActivityMainBinding
import com.google.mediapipe.examples.gesturerecognizer.fragment.FragmentA
import com.google.mediapipe.examples.gesturerecognizer.fragment.FragmentB

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // ignore the reselection
            setContentView(R.layout.activity_main)

            drawerLayout = findViewById(R.id.my_drawer_layout)
            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)

            navigationView = findViewById(R.id.nav_view)
            navigationView.setNavigationItemSelectedListener(this)

            val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FragmentA()).commit()
                navigationView.setCheckedItem(R.id.fragment_a)
            }
        }

    }

    override fun onBackPressed() {
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.fragment_a -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FragmentA()).commit()
            }
            R.id.fragment_b -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FragmentB()).commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}
