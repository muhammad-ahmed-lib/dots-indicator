package com.codetech.lib


import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class DotsIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    // Custom drawable for selected and unselected dots
    private var selectedDotDrawable: Drawable? = null
    private var unselectedDotDrawable: Drawable? = null

    // Fallback colors for selected and unselected dots if drawable is not provided
    private var selectedDotColor: Int = 0
    private var unselectedDotColor: Int = 0

    // Size and spacing of the dots
    private var dotSize: Int = 0
    private var dotSpacing: Int = 0

    // Keeps track of the current selected position
    private var currentPosition: Int = 0
    private var numberOfDots: Int = 0

    // Boolean to determine if dots should be circles or squares
    private var isCircle: Boolean = true

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        // Load custom attributes from XML
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DotsIndicatorView,
            0, 0
        ).apply {
            try {
                dotSize = getDimensionPixelSize(R.styleable.DotsIndicatorView_dotSize, 16)
                dotSpacing = getDimensionPixelSize(R.styleable.DotsIndicatorView_dotSpacing, 8)

                // Retrieve custom drawables if defined
                selectedDotDrawable = getDrawable(R.styleable.DotsIndicatorView_selectedDotDrawable)
                unselectedDotDrawable = getDrawable(R.styleable.DotsIndicatorView_unselectedDotDrawable)

                // Retrieve fallback colors if no drawable is provided
                selectedDotColor = getColor(R.styleable.DotsIndicatorView_selectedDotColor,
                    ContextCompat.getColor(context, android.R.color.white))
                unselectedDotColor = getColor(R.styleable.DotsIndicatorView_unselectedDotColor,
                    ContextCompat.getColor(context, android.R.color.black))

                // Retrieve shape attribute (circle or square)
                isCircle = getBoolean(R.styleable.DotsIndicatorView_dotIsCircle, true)
            } finally {
                recycle()
            }
        }
    }

    // Method to set the number of dots in the indicator
    fun setNumberOfDots(dots: Int) {
        numberOfDots = dots
        removeAllViews() // Clear existing dots

        // Add new dots based on the current position and number of dots
        for (i in 0 until dots) {
            addView(createDot(i == currentPosition))
        }
    }

    // Method to update the current selected position of dots
    fun setCurrentPosition(position: Int) {
        if (position in 0 until numberOfDots) {
            // Update the background of the unselected and selected dots
            getChildAt(currentPosition).background = unselectedDotDrawable ?: createDotDrawable(false)
            getChildAt(position).background = selectedDotDrawable ?: createDotDrawable(true)
            currentPosition = position
        }
    }

    // Create a dot view (either using custom drawable or fallback color)
    private fun createDot(isSelected: Boolean): View {
        val dot = View(context)
        val params = LayoutParams(dotSize, dotSize)
        params.setMargins(dotSpacing, 0, dotSpacing, 0)
        dot.layoutParams = params

        // Set background drawable or color for the dot
        dot.background = if (isSelected) {
            selectedDotDrawable ?: createDotDrawable(true)
        } else {
            unselectedDotDrawable ?: createDotDrawable(false)
        }
        return dot
    }

    // Create a drawable for the dot (circle or square shape)
    private fun createDotDrawable(isSelected: Boolean): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = if (isCircle) GradientDrawable.OVAL else GradientDrawable.RECTANGLE
        drawable.setColor(if (isSelected) selectedDotColor else unselectedDotColor)

        // Apply small rounded corners for squares
        if (!isCircle) drawable.cornerRadius = 8f
        return drawable
    }

    // Attach dots indicator to ViewPager2
    fun attachViewPager(viewPager: ViewPager2) {
        setNumberOfDots(viewPager.adapter?.itemCount ?: 0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setCurrentPosition(position)
            }
        })
    }

    // Attach dots indicator to ViewPager
    fun attachViewPager(viewPager: ViewPager) {
        setNumberOfDots(viewPager.adapter?.count ?: 0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                setCurrentPosition(position)
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
    // Attach dots indicator to RecyclerView for custom paging logic
    fun attachRecyclerView(recyclerView: RecyclerView) {
        setNumberOfDots(recyclerView.adapter?.itemCount ?: 0)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                // Find the center position of the RecyclerView's current visible item
                val layoutManager = recyclerView.layoutManager as? RecyclerView.LayoutManager
                val currentPosition = layoutManager?.getPosition(recyclerView.findViewById(recyclerView.id))
                currentPosition?.let { setCurrentPosition(it) }
            }
        })
    }
}
