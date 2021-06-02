package com.panenin.bangkit.b21.cap0065

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.panenin.bangkit.b21.cap0065.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var dots: Array<TextView?>
    private var dotsLayout: LinearLayout? = null
    private lateinit var layouts: IntArray
    private var viewPager: ViewPager? = null
//    private val myViewPagerAdapter: MyViewPagerAdapter? = null
    private var btnSkip: Button? = null
    private  var btnNext: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        dotsLayout = binding.layoutDots
        btnSkip = binding.btnSkip
        btnNext = binding.btnNext

        layouts = intArrayOf(
                R.layout.welcome_slide1,
                R.layout.welcome_slide_2,
                R.layout.welcome_slide_3,
        )


        // adding bottom dots
        addBottomDots(0)
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        dotsLayout?.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.setText(Html.fromHtml("&#8226;"))
            dots[i]?.setTextSize(35F)
            dots[i]?.setTextColor(colorsInactive[currentPage])
            dotsLayout?.addView(dots[i])
        }
        if (dots.size > 0) dots[currentPage]?.setTextColor(colorsActive[currentPage])
    }

//    class MyViewPagerAdapter : PagerAdapter() {
//        private var layoutInflater: LayoutInflater? = null
//
//        override fun instantiateItem(container: ViewGroup, position: Int): Any {
//            layoutInflater =  getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
//            val view: View = layoutInflater.inflate(layouts.get(position), container, false)
//            container.addView(view)
//            return view
//        }
//
//        val count: Int
//            get() = layouts.size
//
//        fun isViewFromObject(view: View, obj: Any): Boolean {
//            return view === obj
//        }
//
//        fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
//            val view = `object` as View?
//            container.removeView(view)
//        }
//    }
}