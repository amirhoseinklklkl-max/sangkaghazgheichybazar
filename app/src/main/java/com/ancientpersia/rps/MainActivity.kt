package com.ancientpersia.rps

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.ancientpersia.rps.audio.SoundManager
import com.ancientpersia.rps.billing.BazaarIap
import com.ancientpersia.rps.data.GamePrefs
import com.ancientpersia.rps.databinding.ActivityMainBinding
import com.ancientpersia.rps.ui.GameFragment
import com.ancientpersia.rps.ui.MenuFragment
import com.ancientpersia.rps.ui.ShopFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GamePrefs.init(this)
        SoundManager.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val current = supportFragmentManager.findFragmentById(R.id.container)
                if (current is GameFragment || current is ShopFragment) {
                    showMenu()
                } else {
                    finish()
                }
            }
        })

        if (savedInstanceState == null) {
            showMenu()
        }
    }

    fun showMenu() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MenuFragment())
            .commit()
    }

    fun showGame() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, GameFragment())
            .commit()
    }

    fun showShop() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ShopFragment())
            .commit()
    }

    /**
     * نتیجه جریان خرید کافه بازار (طبق مستندات رسمی باید اینجا به کلاس کمکی داده شود)
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!BazaarIap.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        SoundManager.release()
        super.onDestroy()
    }
}
