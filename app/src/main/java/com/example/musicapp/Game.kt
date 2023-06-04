package com.example.musicapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.musicapp.learningModule.TheoreticalFragment
import com.example.musicapp.statWindow.StatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.game.*

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.game)

        val host = NavHostFragment.create(R.navigation.navigation)
        val theoryFragment= TheoreticalFragment()
        val statisticsFragment = StatFragment()

        setCurrentFragment(host)

        val myBottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        myBottomNavigationView.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.levels->{
                    setCurrentFragment(host)
                    print("___first fragment\n")
                    true
                }
                R.id.learning->{
                    setCurrentFragment(theoryFragment)
                    true
                }
                R.id.statistic->{
                    setCurrentFragment(statisticsFragment)
                    true
                }

                else -> {false}
            }
        }
    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}
